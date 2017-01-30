# Product Overview

The existing system allows publishing and subscribing to medical journals in a secure way. The system implements the following specifications:

System has two kind of roles:
- Publisher users
  - A web portal to upload and manage a list of medical journals
  - To upload journals in PDF format
  - Few users can publish journals from one Publisher
  - Delete journals
- Public users
  - A web portal to find and subscribe to journals of their interest
  - Once subscribed the users are able to browse and read journals online. Any other are prohibited
  - Subscription can be canceled

Medical journals are text and image intensive PDF documents. Each journal is owned by some publisher. Each publisher can own one or more journals. Each journal can be part of a category. Each journal has multiple issues/editions that are released on a regular basis. Public users can subscribe to multiple categories. Once subscribed all journals of a category are available to the user.

# Installation
## Prerequisites
1. Java 8
2. Maven 3.3+
3. MySQL 5.6+
4. Internet connection (fonts are loaded from 3rd party website)

## Run instructions
1. Configure the database connection data from `Code/src/main/resources/application.properties`
1.1. In case of you had already initialized database. Use scripts from SQL folder to make database up-to-date. It will be done automatically by application in case of it has enough permissions.
2. Go to the Code folder and run `mvn spring-boot:run`.
3. Go to http://localhost:8080 url
4. *(Optional)* By default, the application stores the uploaded PDFs in <User_home>/upload directory. If you want to change this directory, you can use the `-Dupload-dir=<path>` system property.
5. *(Optional)* The PDFs for the predefined journals can be found in the PDFs folder. If you want to view the predefined journals, you should copy the contents of this folder to the upload folder defined in step 4.

## Authentication data
The system comes with 4 predefined user accounts. They are:
1. publishers:
 * username: publisher1 / password: publisher1
 * username: publisher2 / password: publisher2
2. public users:
 - username: user1 / password: user1
 - username: user2 / password: user2

## Deployment
Application is cross-platform and can be deployed in cloud VMs or build into docker containers.

## Summary
1. Email notification with Journals digest. It is initiating by scheduler with crontab expression. Application sends digest only in case of any new journals appeared last day. Only journals from previous day will come to digest email notification. 
2. Email notification for newly published journal for subscribers. Message driven approach is used to initiate notification. So, it is not block main thread and will be executed almost immediately in background.
3. SMTP reliability implemented using retry pattern with growing delay on each attempt.
