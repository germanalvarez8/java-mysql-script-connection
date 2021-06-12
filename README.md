# java-mysql-script-connection

Este repositorio tiene como objetivo cumplir con la tercera entrega del trabajo practico de base de datos
de la UNRC en el primer cuatrimestre del año 2021

## Requerimientos

1. Instalar Java 8 o superior:
    [java 11 on Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
2. Instalar Mysql 8:
    - [Mysql on linux](https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-20-04)
    - [Mysql on windows](https://smarttechnicalworld.com/how-to-install-mysql-on-windows-10/)
    - [Mysql on linux](https://www.positronx.io/how-to-install-mysql-on-mac-configure-mysql-in-terminal/)
3. Instalar Git:
```bash
    sudo apt install git-all
```
4. Clave SSH (OPCIONAL)
    [Generar clave ssh](https://help.github.com/es/github/authenticating-to-github/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent)
5. Linkear clave SSH con tu cuenta de GitHub (OPCIONAL)
    [Linkear clave ssh](https://help.github.com/es/github/authenticating-to-github/adding-a-new-ssh-key-to-your-github-account)

Si cuenta con la clave SSH en su cuenta de GitHub, clonar el repositorio de la siguiente manera

```bash
    git clone git@github.com:germanalvarez8/java-mysql-script-connection.git
```

Si no cuenta con la clave SSH, clonar de la siguiente manera:

```bash
    git clone https://github.com/germanalvarez8/java-mysql-script-connection.git
```

## Estructura del repositorio

```bash
├── java-mysql-script-connection
│   ├── src
│   │   ├── database_script.txt
│   │   ├── MysqCrud.java
│   └── README.md
└── Others Projects
```

## Observaciones

En el archivo MysqlCrud.java se deben completar las siguientes variables
```bash
    String url  = "jdbc:mysql://localhost:3306/{{Database Name}}";
    String user = "{{Mysql user}}";
    String pass = "{{Mysql password}}";
```
Completando con el nombre, usuario y password de su base de datos