-- Extensión para UUIDs (si no está habilitada)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE subscription_type AS ENUM (
    'TEAM',
    'FIXTURE'
);

CREATE TYPE notification_event AS ENUM (
    'MATCH_START',
    'MATCH_END',
    'GOAL',
    'CARD'
);

CREATE TYPE notification_channel AS ENUM (
    'LOG',
    'WEBHOOK'
);

CREATE TYPE subscription_status AS ENUM (
    'ACTIVE',
    'CANCELLED'
);

CREATE TABLE subscriptions (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               user_id UUID NOT NULL,
                               type subscription_type NOT NULL,
                               team_id INTEGER,
                               fixture_id INTEGER,
                               events notification_event[] NOT NULL,
                               channel notification_channel NOT NULL,
                               webhook_url TEXT,
                               status subscription_status NOT NULL,
                               created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_subscriptions_user_id_status ON subscriptions(user_id, status);
