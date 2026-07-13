ALTER TABLE IF EXISTS group_messages
    ALTER COLUMN message_text TYPE TEXT;

ALTER TABLE IF EXISTS group_messages
    ADD COLUMN IF NOT EXISTS message_type VARCHAR(20);
