#!/usr/bin/env node

/**
 * Newman CLI Runner Profesional
 * Ejecuta colecciones Postman con control de errores, retries y reportes
 */

const fs = require('fs');
const path = require('path');
const { spawn } = require('child_process');

// ============================
// CONFIG
// ============================

const args = process.argv.slice(2);

const config = {
  testFolder: args[0] || './postman-tests',
  environmentFile: args[1] || null,
  maxRetries: 3,
  delay: 1000,
  stopOnFailure: args.includes('--stop-on-failure'),
  verbose: args.includes('--verbose') || args.includes('-v')
};

// ============================
// UTILS
// ============================

const sleep = (ms) => new Promise(r => setTimeout(r, ms));

function exists(p) {
  return fs.existsSync(p);
}

function isDir(p) {
  return exists(p) && fs.statSync(p).isDirectory();
}

// ============================
// DISCOVERY
// ============================

function findCollections(dir) {
  const results = [];

  function walk(current) {
    const items = fs.readdirSync(current);

    for (const item of items) {
      const full = path.join(current, item);
      const stat = fs.statSync(full);

      if (stat.isDirectory()) {
        walk(full);
      } else if (item.endsWith('.json')) {
        try {
          const json = JSON.parse(fs.readFileSync(full, 'utf8'));

          if (json.info && json.item) {
            results.push({
              name: json.info.name || item,
              path: full
            });
          }
        } catch (_) {}
      }
    }
  }

  walk(dir);
  return results;
}

// ============================
// EXECUTION
// ============================

function runNewman(collection) {
  return new Promise((resolve) => {
    const args = ['run', collection.path];

    if (config.environmentFile) {
      args.push('--environment', config.environmentFile);
    }

    const proc = spawn('newman', args, { env: process.env });

    let stdout = '';
    let stderr = '';

    proc.stdout.on('data', d => {
      const s = d.toString();
      stdout += s;
      process.stdout.write(s);
    });

    proc.stderr.on('data', d => {
      stderr += d.toString();
    });

    proc.on('close', code => {
      resolve({
        success: code === 0,
        exitCode: code,
        stdout,
        stderr
      });
    });

    proc.on('error', err => {
      resolve({ success: false, error: err.message });
    });
  });
}

async function runWithRetry(collection) {
  let last;

  for (let i = 1; i <= config.maxRetries; i++) {
    console.log(`\n🔄 ${collection.name} (intento ${i})`);

    const res = await runNewman(collection);
    last = res;

    if (res.success) return res;

    if (i < config.maxRetries) {
      console.log('⚠️ Reintentando...');
      await sleep(2000);
    }
  }

  return { ...last, retries: config.maxRetries };
}

// ============================
// REPORT
// ============================

function summary(results) {
  const total = results.length;
  const passed = results.filter(r => r.success).length;
  const failed = total - passed;

  console.log('\n==============================');
  console.log('📊 RESULTADO FINAL');
  console.log('==============================');
  console.log(`Total: ${total}`);
  console.log(`✅ OK: ${passed}`);
  console.log(`❌ FAIL: ${failed}`);
}

function htmlReport(results) {
  const dir = path.join(config.testFolder, 'reports');
  fs.mkdirSync(dir, { recursive: true });

  const file = path.join(dir, `report-${Date.now()}.html`);

  const html = `
  <html>
  <body>
  <h1>Newman Report</h1>
  <table border="1">
  <tr><th>Name</th><th>Status</th></tr>
  ${results.map(r => `
    <tr>
      <td>${r.name}</td>
      <td>${r.success ? 'OK' : 'FAIL'}</td>
    </tr>
  `).join('')}
  </table>
  </body>
  </html>
  `;

  fs.writeFileSync(file, html);
  console.log('📄 Report:', file);
}

// ============================
// MAIN
// ============================

async function main() {
  console.log('🚀 Newman CLI Runner');

  if (!isDir(config.testFolder)) {
    console.error('❌ Carpeta no existe');
    process.exit(1);
  }

  const collections = findCollections(config.testFolder);

  if (!collections.length) {
    console.error('❌ No hay colecciones');
    process.exit(1);
  }

  console.log(`📦 ${collections.length} colecciones encontradas`);

  const results = [];

  for (const col of collections) {
    const res = await runWithRetry(col);

    results.push({ ...res, name: col.name });

    if (!res.success && config.stopOnFailure) {
      console.error('⛔ Stop por fallo');
      break;
    }

    await sleep(config.delay);
  }

  summary(results);
  htmlReport(results);

  const failed = results.some(r => !r.success);
  process.exit(failed ? 1 : 0);
}

if (require.main === module) {
  main().catch(err => {
    console.error('❌ Fatal:', err);
    process.exit(1);
  });
}

module.exports = { main };