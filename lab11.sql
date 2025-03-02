SELECT 
  engine, 
  support 
FROM 
  information_schema.engines 
ORDER BY 
  engine;


create user bob@localhost 
identified by 'Secure1pass!';

SELECT
  user 
FROM 
  mysql.user;

show databases;

grant all privileges on bobdb.* to bob@localhost;

GRANT SELECT 
ON *.* 
TO bob@localhost;

GRANT INSERT 
ON classicmodels.* 
TO bob@localhost;

GRANT 
   SELECT (employeeNumner,lastName, firstName,email), 
   UPDATE(lastName) 
ON employees 
TO bob@localhost;

GRANT EXECUTE 
ON PROCEDURE CheckCredit 
TO bob@localhost;

CREATE USER super@localhost 
IDENTIFIED BY 'Secure1Pass!';

SHOW GRANTS FOR super@localhost;

CREATE USER rfc@localhost 
IDENTIFIED BY 'Secret1Pass!';

GRANT SELECT, UPDATE, INSERT 
ON classicmodels.*
TO rfc@localhost;

REVOKE INSERT, UPDATE
ON classicmodels.*
FROM rfc@localhost;

SHOW GRANTS FOR rfc@localhost;

GRANT EXECUTE
ON classicmodels.*
TO rfc@localhost;

REVOKE ALL, GRANT OPTION
FROM rfc@localhost;

SHOW GRANTS FOR rfc@localhost;

CREATE ROLE 
    crm_dev, 
    crm_read, 
    crm_write;
GRANT ALL 
ON crm.* 
TO crm_dev;

GRANT SELECT 
ON crm.* 
TO crm_read;

GRANT INSERT, UPDATE, DELETE
ON crm.* 
TO crm_write;

-- developer user 
CREATE USER crm_dev1@localhost IDENTIFIED BY 'Secure$1782';

-- read access user
CREATE USER crm_read1@localhost IDENTIFIED BY 'Secure$5432';    

-- read/write users
CREATE USER crm_write1@localhost IDENTIFIED BY 'Secure$9075';   
CREATE USER crm_write2@localhost IDENTIFIED BY 'Secure$3452';

GRANT crm_dev 
TO crm_dev1@localhost;

GRANT crm_read 
TO crm_read1@localhost;

GRANT crm_read, 
    crm_write 
TO crm_write1@localhost, 
    crm_write2@localhost;

SHOW GRANTS 
FOR crm_write1@localhost 
USING crm_write;


GRANT crm_dev1@localhost 
TO crm_dev2@localhost;


CREATE ROLE admin_role;
GRANT ALL PRIVILEGES ON *.* TO admin_role WITH GRANT OPTION;
select Role from user;

CREATE ROLE query_role;
GRANT SELECT ON *.* TO query_role;

