vault server --dev --dev-root-token-id="00000000-0000-0000-0000-000000000000"

set VAULT_TOKEN=00000000-0000-0000-0000-000000000000
set VAULT_ADDR=http://127.0.0.1:8200

vault kv put secret/vault-config-app spring.datasource.username=root spring.datasource.password=srivatsan

vault kv get secret/vault-config-app
