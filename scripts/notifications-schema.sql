-- Extensión para UUIDs (si no está habilitada)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE subscriptions (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               user_id UUID NOT NULL,
                               type VARCHAR(20) NOT NULL,
                               team_id INTEGER,
                               fixture_id INTEGER,
                               events VARCHAR(20)[] NOT NULL,
                               channel VARCHAR(20) NOT NULL,
                               webhook_url TEXT,
                               status VARCHAR(20) NOT NULL,
                               created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_subscriptions_user_id_status ON subscriptions(user_id, status);
