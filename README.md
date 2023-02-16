# XMLToSQLParser

- [Description](#description)
- [Demo](#demo)
- [Basic usage](#basic-usage)
- [Interface](#interface)
- [Libraries&Framework](#librariesframework)

## Description

Parse Obec and CastObce from given zipped XML file (from url). Application consist of backend part written in **Java** with framework **Spring boot**, frontend part written mostly using **ReactJS** and **MySQL** database. Application is containerized using *Docker Compose*.

## Demo

MISSING :<

## Basic usage

Application can be build using standard Docker Compose commands:

    - `docker compose up` to build application
    - `docker compose pause` to pause execution of container
    - `docker compose dawn` to remove containers

## Interface

Interface is yet simple, but useful. User can enter url inside box and using `Send Url` button send request to server to process zip available by this url. User will be informed via notification if command was successful or error will be printed. After successful operation user can print information about MySQL DB using `Dumb db` button. In following text area will appear result of this request. Also user is available to clear full information in MySQL tables using `Clear tables` button.

## Libraries&Framework

- Frontend 
    - [ReactJS](https://reactjs.org/)
    - [Bootstrap-react](https://react-bootstrap.github.io)
    - [Axios](https://axios-http.com)
- Backend
    - [Spring boot](https://spring.io)

