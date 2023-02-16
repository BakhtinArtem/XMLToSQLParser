import axios from 'axios';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import {Card, Col, Container, Navbar, Row} from 'react-bootstrap'
import { useState, useEffect, useCallback } from 'react';
import Alert from 'react-bootstrap/Alert';

function App() {
  const [url, setUrl] = useState()
  const [text, setText] = useState("")
  const [show, setShow] = useState(false)
  const [response, setResponse] = useState("Invalid")

  const saveUrl = "http://localhost:8080/saveUrl";
  const getObec = "http://localhost:8080/getObec";
  const getCastObce = "http://localhost:8080/getCastObce";
  const deleteAll = "http://localhost:8080/deleteAll";

  const onUrlChange = useCallback(e => {
    setUrl(e.target.value)
  })

  const processFormSubmission = async(e) => {
    e.preventDefault();
    try {
      const resp = await axios({
        method: 'post',
        withCredentials: false,
        url: saveUrl,
        headers: {
          "Access-Control-Allow-Origin": "*"
        },
        data: {
          "url": url
        }
      });
      setResponse(resp.data)
    } catch (error) {
      setResponse(error.message)
    }
    setShow(true)
  }

  const processDumbDb = async(e) => {
    e.preventDefault();
    try {
      const respObec = await axios({
        method: 'get',
        withCredentials: false,
        url: getObec,
        headers: {
          "Access-Control-Allow-Origin": "*"
        },
      });

      const respCastObce = await axios({
        method: 'get',
        withCredentials: false,
        url: getCastObce,
        headers: {
          "Access-Control-Allow-Origin": "*"
        },
      });

      setText(
        "Obce\n" +
        JSON.stringify(respObec.data) + 
        '\n==========================\n' +
        "CastObce\n" +
        JSON.stringify(respCastObce.data))
      setResponse("Got data")
    } catch (error) {
      setResponse(error.message)
    }
    setShow(true)
  }

  const processTableClear = async(e) => {
    e.preventDefault();
    try {
      const resp = await axios({
        method: 'get',
        withCredentials: false,
        url: deleteAll,
        headers: {
          "Access-Control-Allow-Origin": "*"
        },
      });
      setResponse("Deleted")
    } catch (error) {
      setResponse(error.message)
    }
    setShow(true)
  }

  return (
    <>
    
      <Navbar bg='dark' expand='lg'>
        <Container>
          <Navbar.Brand>
            <p style={{color: "white"}}>XMLToSQL</p>
          </Navbar.Brand>
        </Container>
      </Navbar>

      <Container style={{ position: "absolute", top: 0, left: 0, right: 0, zIndex: 999 }} >
        <Alert
          variant='dark'
          dismissible
          show={show}
          onClose={(e) => setShow(false)}
        >
          {response}
        </Alert>
      </Container>


      <Container id='form-container' className='p-0 mt-3'>
        <Card>
          <Card.Body>
            <Form>
              <Form.Group className="mb-3" controlId="URL">
                <Form.Label className="text-center">URL</Form.Label>
                <Form.Control 
                  type="text"
                  placeholder="Enter URL:"
                  onChange={onUrlChange}
                />
              </Form.Group>
              <Container>
                <Row>
                  <Col className='text-center'>
                    <Button
                      variant="dark"
                      type="submit"
                      onClick={processFormSubmission}
                    >
                      Send Url
                    </Button>
                  </Col>
                  <Col className='text-center'>
                    <Button
                      variant="dark"
                      type="submit"
                      onClick={processDumbDb}
                    >
                       Dumb db
                    </Button>
                  </Col>
                  <Col className='text-center'>
                    <Button
                      variant="dark"
                      type="submit"
                      onClick={processTableClear}
                    >
                      Clear tables
                    </Button>
                  </Col>
                </Row>
              </Container>
            </Form>
          </Card.Body>
        </Card>
      </Container>

      <Container className='p-0 mt-3'>
        <Form>
          <Form.Group className="mb-3" controlId="TextArea">
            <Form.Label>Output</Form.Label>
            <Form.Control as="textarea"
              value={text}
              rows={10}
              onChange={(e) => setText(e.target.value)}
            />
          </Form.Group>
        </Form>
      </Container>
    </>
  );
}

export default App;
