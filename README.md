# Projecte Java Aparkarma - Gestió d'Usuaris

Aquest projecte és una part inicial de l'aplicació Aparkarma, que gestiona usuaris i les seves sessions mitjançant un sistema d'autenticació i autorització. 
Utilitza PostgreSQL com a base de dades per emmagatzemar la informació dels usuaris.

## Requisits previs

- **Java JDK 17**: Assegura't de tenir instal·lat Java JDK 17.
- **PostgreSQL**: Has de tenir instal·lat i configurat PostgreSQL.
  - **Usuari**: `postgres`
  - **Clau**: `3409`
  - **Base de dades**: `postgres`
  - **Esquema**: `public`
- **Maven**: Per gestionar les dependències i compilar el projecte.
-**Port**: En el cas de l’app movil utilitzar el port: 12345.

## Funcionament

### 1. Iniciar el Servidor
El servidor s'encarrega de gestionar les connexions dels clients, autenticar usuaris i gestionar les seves sessions.

### 2. Iniciar els clients
Els clients s'encarreguen de gestionar les solicituds del usuaris i soliciten respostes al servidor.
### 2.1 Aplicació Movil
Al iniciar apareix el home on es podrà triar si iniciar sessió o crear un usuari nou.
### 2.1.1 Crear Usuari Nou
Es necessitarà un nom, usuari i una contrasenya de 12 caràcters amb majúscules, minúscules i símbols.
### 2.1.2 Login
Pel login s’ha d’introduïr un usuari i contrasenya correctes.
### 2.1.3 Pantalla principal
Un cop iniciada la sessió, s’obrirà la pantalla d’usuari on es podrà començar a buscar pàrquing. Allà mateix es pot fer el logout.
