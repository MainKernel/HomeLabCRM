# ENV VARS

## BACKEND

POSTGRESQL 
--------------
* DB_HOST - database host
* DB_PORT - database port
* DB_NAME - database name
* DB_USER - database user
* DB_PASSWORD - database password


Mail
-----------------
* MAIL_HOST - mail host
* MAIL_PORT - mail port
* MAIL_USERNAME - mail username
* MAIL_PASSWORD - mail password
* MAIL_SMTP_AUTH - mail smtp auth (true | false)
* MAIL_STARTTLS - mail star tls (true | false)

SECURITY
------------------
* JWT_SECRET - jwt secret token
* JWT_EXPIRATION - jwt expiration (86400000)

FILES
-------------
* ITEM_PREVIEW_UPLOAD_DIR - dir for item preview images
* ITEM_DOCUMENTS_UPLOAD_DIR - dir for item documents upload


## Frontend 

---
* VITE_API_BASE_URL - backend base url (+ /api)