# Online Student Management System (Spring + Hibernate)

## Overview
Mini project demonstrating Spring DI, Hibernate ORM, transactions for fee/payment, and CRUD operations for students.

## Structure
- `src/main/java/com/example/controller/` — all Java files (entities, dao, service, config, main)
- `src/main/resources/` — resources (hibernate.cfg.xml)
- `pom.xml` — Maven build
- `.gitignore` — ignore target and credentials

## Setup (local)
1. Install Java JDK and Maven.
2. Install & start MySQL, create a database:
   ```sql
   CREATE DATABASE studentdb;
