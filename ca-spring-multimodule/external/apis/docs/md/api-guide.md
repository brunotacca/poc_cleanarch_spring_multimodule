# 

**API Guide from [CA Spring
Multimodule](https://github.com/brunotacca/poc_cleanarch_spring_multimodule)**

# HTTP verbs

CA Spring Multimodule tries to adhere as closely as possible to standard
HTTP and REST conventions in its use of HTTP verbs.

<table>
<colgroup>
<col style="width: 50%" />
<col style="width: 50%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Verb</th>
<th style="text-align: left;">Usage</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>GET</code></p></td>
<td style="text-align: left;"><p>Used to retrieve a resource</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>POST</code></p></td>
<td style="text-align: left;"><p>Used to create a new resource</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>PATCH</code></p></td>
<td style="text-align: left;"><p>Used to update an existing resource,
including partial updates</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>DELETE</code></p></td>
<td style="text-align: left;"><p>Used to delete an existing
resource</p></td>
</tr>
</tbody>
</table>

CA Spring Multimodule tries to adhere as closely as possible to standard
HTTP and REST conventions in its use of HTTP status codes.

<table>
<colgroup>
<col style="width: 50%" />
<col style="width: 50%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Status code</th>
<th style="text-align: left;">Usage</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>200 OK</code></p></td>
<td style="text-align: left;"><p>The request completed
successfully</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>201 Created</code></p></td>
<td style="text-align: left;"><p>A new resource has been created
successfully. The resource’s URI is available from the response’s
<code>Location</code> header</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>204 No Content</code></p></td>
<td style="text-align: left;"><p>An update to an existing resource has
been applied successfully</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>400 Bad Request</code></p></td>
<td style="text-align: left;"><p>The request was malformed. The response
body will include an error providing further information</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>404 Not Found</code></p></td>
<td style="text-align: left;"><p>The requested resource did not
exist</p></td>
</tr>
</tbody>
</table>

# Hypermedia

CA Spring Multimodule uses hypermedia and resources include links to
other resources in their responses. Responses are in [Hypertext
Application from resource to resource. Language
(HAL)](http://stateless.co/hal_specification.html) format. Links can be
found beneath the `_links` key. Users of the API should not create URIs
themselves, instead they should use the above-described links to
navigate

# Index

The index provides the entry point into the service.

## Accessing the index

A `GET` request is used to access the index

### Request structure

    GET / HTTP/1.1
    Host: localhost:8080

### Example response

    HTTP/1.1 200 OK
    Content-Type: application/prs.hal-forms+json
    Content-Length: 201

    {
      "_links" : {
        "customers" : {
          "href" : "http://localhost:8080/customers/id"
        },
        "customers:search" : {
          "href" : "http://localhost:8080/customers?name=customerName"
        }
      }
    }

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Path</th>
<th style="text-align: left;">Type</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>_links</code></p></td>
<td style="text-align: left;"><p><code>Object</code></p></td>
<td style="text-align: left;"><p>Links to resources</p></td>
</tr>
</tbody>
</table>

### CURL request

    $ curl 'http://localhost:8080/' -i -X GET

### Links

<table>
<colgroup>
<col style="width: 50%" />
<col style="width: 50%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Relation</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>customers</code></p></td>
<td style="text-align: left;"><p>The Customers resource</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>customers:search</code></p></td>
<td style="text-align: left;"><p>The Search for Customers</p></td>
</tr>
</tbody>
</table>

# Customer REST Service

The `customers` provides the entry point into the service.

## (POST) Creating customers

A `POST` request is used to access customer creation.

### Request structure

    POST /customers HTTP/1.1
    Content-Type: application/hal+json;charset=UTF-8
    Content-Length: 139
    Host: localhost:8080

    {
      "zip" : "000000-000",
      "number" : "123-A",
      "city" : "city",
      "street" : "street",
      "name" : "Foo Bar",
      "email" : "foo@bar.com"
    }

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Path</th>
<th style="text-align: left;">Type</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>name</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>The name of the customer</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>email</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>The email of the customer</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>street</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>The street of the customer</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>number</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>The number of the customer</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>city</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>The city of the customer</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>zip</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>The zip of the customer</p></td>
</tr>
</tbody>
</table>

### Example response

    HTTP/1.1 201 Created
    Location: /91f9d915-d1ea-4eb8-97d1-cfabdffc5085
    Content-Type: application/prs.hal-forms+json
    Content-Length: 129

    {
      "_links" : {
        "edit" : {
          "href" : "http://localhost:8080/customers/91f9d915-d1ea-4eb8-97d1-cfabdffc5085"
        }
      }
    }

### Response headers

<table>
<colgroup>
<col style="width: 50%" />
<col style="width: 50%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Name</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>Location</code></p></td>
<td style="text-align: left;"><p>The location of the created
resource</p></td>
</tr>
</tbody>
</table>

### CURL request

    $ curl 'http://localhost:8080/customers' -i -X POST \
        -H 'Content-Type: application/hal+json;charset=UTF-8' \
        -d '{
      "zip" : "000000-000",
      "number" : "123-A",
      "city" : "city",
      "street" : "street",
      "name" : "Foo Bar",
      "email" : "foo@bar.com"
    }'

### Links

<table>
<colgroup>
<col style="width: 50%" />
<col style="width: 50%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Relation</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>edit</code></p></td>
<td style="text-align: left;"><p>Edit customer data</p></td>
</tr>
</tbody>
</table>

## (GET) Retrieving customers by ID

A `GET` request is used to get the customer by Id.

### Request structure

    GET /customers/e3119506-030a-4877-a219-389ef21118a4 HTTP/1.1
    Accept: application/prs.hal-forms+json
    Host: localhost:8080

<table>
<caption>/customers/{id}</caption>
<colgroup>
<col style="width: 50%" />
<col style="width: 50%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Parameter</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>id</code></p></td>
<td style="text-align: left;"><p>Customer Id</p></td>
</tr>
</tbody>
</table>

/customers/{id}

### CURL request

    $ curl 'http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4' -i -X GET \
        -H 'Accept: application/prs.hal-forms+json'

### Example response

    HTTP/1.1 200 OK
    Content-Type: application/prs.hal-forms+json
    Content-Length: 1533

    {
      "id" : "e3119506-030a-4877-a219-389ef21118a4",
      "name" : "Foo Bar",
      "email" : "foo@bar.com",
      "active" : false,
      "street" : "street",
      "number" : "123-A",
      "city" : "city",
      "zip" : "000000-000",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4"
        }
      },
      "_templates" : {
        "default" : {
          "method" : "PUT",
          "properties" : [ {
            "name" : "city",
            "regex" : "^(?=\\s*\\S).*$",
            "required" : true,
            "type" : "text"
          }, {
            "name" : "email",
            "regex" : "^(?=\\s*\\S).*$",
            "required" : true,
            "type" : "text"
          }, {
            "name" : "name",
            "regex" : "^(?=\\s*\\S).*$",
            "required" : true,
            "type" : "text"
          }, {
            "name" : "number",
            "regex" : "^(?=\\s*\\S).*$",
            "required" : true,
            "type" : "text"
          }, {
            "name" : "street",
            "regex" : "^(?=\\s*\\S).*$",
            "required" : true,
            "type" : "text"
          }, {
            "name" : "zip",
            "regex" : "^(?=\\s*\\S).*$",
            "required" : true,
            "type" : "text"
          } ]
        },
        "activate" : {
          "method" : "PATCH",
          "properties" : [ ],
          "target" : "http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4/activate"
        },
        "deactivate" : {
          "method" : "PATCH",
          "properties" : [ ],
          "target" : "http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4/deactivate"
        }
      }
    }

## (GET) Finding customers by Name

A `GET` request is also used to find customers by name.

### Request structure

    GET /customers?name=Foo%20Bar HTTP/1.1
    Accept: application/prs.hal-forms+json
    Host: localhost:8080

<table>
<colgroup>
<col style="width: 50%" />
<col style="width: 50%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Parameter</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>name</code></p></td>
<td style="text-align: left;"><p>Customer name to search</p></td>
</tr>
</tbody>
</table>

### CURL request

    $ curl 'http://localhost:8080/customers?name=Foo%20Bar' -i -X GET \
        -H 'Accept: application/prs.hal-forms+json'

### Response structure

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Path</th>
<th style="text-align: left;">Type</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td
style="text-align: left;"><p><code>_embedded.existingCustomerModelList</code></p></td>
<td style="text-align: left;"><p><code>Array</code></p></td>
<td style="text-align: left;"><p>The customer list</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>_links</code></p></td>
<td style="text-align: left;"><p><code>Object</code></p></td>
<td style="text-align: left;"><p>The search made</p></td>
</tr>
</tbody>
</table>

### Example response

    HTTP/1.1 200 OK
    Content-Type: application/prs.hal-forms+json
    Content-Length: 1942

    {
      "_embedded" : {
        "existingCustomerModelList" : [ {
          "id" : "e3119506-030a-4877-a219-389ef21118a4",
          "name" : "Foo Bar",
          "email" : "foo@bar.com",
          "active" : false,
          "street" : "street",
          "number" : "123-A",
          "city" : "city",
          "zip" : "000000-000",
          "_links" : {
            "self" : {
              "href" : "http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4"
            }
          },
          "_templates" : {
            "default" : {
              "method" : "PUT",
              "properties" : [ {
                "name" : "city",
                "regex" : "^(?=\\s*\\S).*$",
                "required" : true,
                "type" : "text"
              }, {
                "name" : "email",
                "regex" : "^(?=\\s*\\S).*$",
                "required" : true,
                "type" : "text"
              }, {
                "name" : "name",
                "regex" : "^(?=\\s*\\S).*$",
                "required" : true,
                "type" : "text"
              }, {
                "name" : "number",
                "regex" : "^(?=\\s*\\S).*$",
                "required" : true,
                "type" : "text"
              }, {
                "name" : "street",
                "regex" : "^(?=\\s*\\S).*$",
                "required" : true,
                "type" : "text"
              }, {
                "name" : "zip",
                "regex" : "^(?=\\s*\\S).*$",
                "required" : true,
                "type" : "text"
              } ]
            },
            "activate" : {
              "method" : "PATCH",
              "properties" : [ ],
              "target" : "http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4/activate"
            },
            "deactivate" : {
              "method" : "PATCH",
              "properties" : [ ],
              "target" : "http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4/deactivate"
            }
          }
        } ]
      },
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/customers?name=Foo%20Bar"
        }
      }
    }

## (PUT) Updating customers

A `PUT` request is used to access customer update.

### Request structure

    PUT /customers/e3119506-030a-4877-a219-389ef21118a4 HTTP/1.1
    Content-Type: application/hal+json;charset=UTF-8
    Content-Length: 139
    Host: localhost:8080

    {
      "zip" : "000000-000",
      "number" : "123-A",
      "city" : "city",
      "street" : "street",
      "name" : "Foo Bar",
      "email" : "foo@bar.com"
    }

<table>
<caption>/customers/{id}</caption>
<colgroup>
<col style="width: 50%" />
<col style="width: 50%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Parameter</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>id</code></p></td>
<td style="text-align: left;"><p>Customer Id</p></td>
</tr>
</tbody>
</table>

/customers/{id}

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Path</th>
<th style="text-align: left;">Type</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>name</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>The name of the customer</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>email</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>The email of the customer</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>street</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>The street of the customer</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>number</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>The number of the customer</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>city</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>The city of the customer</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>zip</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>The zip of the customer</p></td>
</tr>
</tbody>
</table>

### CURL request

    $ curl 'http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4' -i -X PUT \
        -H 'Content-Type: application/hal+json;charset=UTF-8' \
        -d '{
      "zip" : "000000-000",
      "number" : "123-A",
      "city" : "city",
      "street" : "street",
      "name" : "Foo Bar",
      "email" : "foo@bar.com"
    }'

### Example response

    HTTP/1.1 200 OK
    Content-Type: application/prs.hal-forms+json
    Content-Length: 1533

    {
      "id" : "e3119506-030a-4877-a219-389ef21118a4",
      "name" : "Foo Bar",
      "email" : "foo@bar.com",
      "active" : false,
      "street" : "street",
      "number" : "123-A",
      "city" : "city",
      "zip" : "000000-000",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4"
        }
      },
      "_templates" : {
        "default" : {
          "method" : "PUT",
          "properties" : [ {
            "name" : "city",
            "regex" : "^(?=\\s*\\S).*$",
            "required" : true,
            "type" : "text"
          }, {
            "name" : "email",
            "regex" : "^(?=\\s*\\S).*$",
            "required" : true,
            "type" : "text"
          }, {
            "name" : "name",
            "regex" : "^(?=\\s*\\S).*$",
            "required" : true,
            "type" : "text"
          }, {
            "name" : "number",
            "regex" : "^(?=\\s*\\S).*$",
            "required" : true,
            "type" : "text"
          }, {
            "name" : "street",
            "regex" : "^(?=\\s*\\S).*$",
            "required" : true,
            "type" : "text"
          }, {
            "name" : "zip",
            "regex" : "^(?=\\s*\\S).*$",
            "required" : true,
            "type" : "text"
          } ]
        },
        "activate" : {
          "method" : "PATCH",
          "properties" : [ ],
          "target" : "http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4/activate"
        },
        "deactivate" : {
          "method" : "PATCH",
          "properties" : [ ],
          "target" : "http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4/deactivate"
        }
      }
    }

### Links

<table>
<colgroup>
<col style="width: 50%" />
<col style="width: 50%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Relation</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>self</code></p></td>
<td style="text-align: left;"><p>Resource Self Link</p></td>
</tr>
</tbody>
</table>

## (PATCH) Activate customer

A `PATCH` request can be used to activate a customer.

### Request structure

    PATCH /customers/e3119506-030a-4877-a219-389ef21118a4/activate HTTP/1.1
    Host: localhost:8080

<table>
<caption>/customers/{id}/activate</caption>
<colgroup>
<col style="width: 50%" />
<col style="width: 50%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Parameter</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>id</code></p></td>
<td style="text-align: left;"><p>Customer Id</p></td>
</tr>
</tbody>
</table>

/customers/{id}/activate

### CURL request

    $ curl 'http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4/activate' -i -X PATCH

### Example response

    HTTP/1.1 202 Accepted
    Content-Type: application/prs.hal-forms+json
    Content-Length: 254

    {
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4"
        },
        "deactivate" : {
          "href" : "http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4/deactivate"
        }
      }
    }

### Links

<table>
<colgroup>
<col style="width: 50%" />
<col style="width: 50%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Relation</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>self</code></p></td>
<td style="text-align: left;"><p>Resource Self Link</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>deactivate</code></p></td>
<td style="text-align: left;"><p>Link to deactivate the
customer</p></td>
</tr>
</tbody>
</table>

## (PATCH) Deactivate customer

A `PATCH` request can be used to deactivate a customer.

### Request structure

    PATCH /customers/e3119506-030a-4877-a219-389ef21118a4/deactivate HTTP/1.1
    Host: localhost:8080

<table>
<caption>/customers/{id}/deactivate</caption>
<colgroup>
<col style="width: 50%" />
<col style="width: 50%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Parameter</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>id</code></p></td>
<td style="text-align: left;"><p>Customer Id</p></td>
</tr>
</tbody>
</table>

/customers/{id}/deactivate

### CURL request

    $ curl 'http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4/deactivate' -i -X PATCH

### Example response

    HTTP/1.1 202 Accepted
    Content-Type: application/prs.hal-forms+json
    Content-Length: 250

    {
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4"
        },
        "activate" : {
          "href" : "http://localhost:8080/customers/e3119506-030a-4877-a219-389ef21118a4/activate"
        }
      }
    }

### Links

<table>
<colgroup>
<col style="width: 50%" />
<col style="width: 50%" />
</colgroup>
<thead>
<tr class="header">
<th style="text-align: left;">Relation</th>
<th style="text-align: left;">Description</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p><code>self</code></p></td>
<td style="text-align: left;"><p>Resource Self Link</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>activate</code></p></td>
<td style="text-align: left;"><p>Link to activate the customer</p></td>
</tr>
</tbody>
</table>

# References
