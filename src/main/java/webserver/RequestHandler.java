package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.StringTokenizer;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import static util.HttpRequestUtils.createUser;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            String path=null;

            if (line.contains("POST")) {
                handlePost(out, br);
            }
            handleGET(out, line);
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void handleGET(OutputStream out, String line) throws IOException {
        String path;
        path = HttpRequestUtils.getUrl(line);
        response(out, path,"202");
    }

    private void handlePost(OutputStream out, BufferedReader br) throws IOException {
        String line;
        String path;
        int contentLength = 0;
        String host=null;
        while (true) {
            line = br.readLine();
            log.info("cur line: {}", line);
            if ("".equals(line)) {
                break;
            }
            if (line.contains("Content-Length:")) {
                contentLength = Integer.parseInt(line.split(" ")[1]);
            }
            if (line.contains("Host:")) {
                host = line.split(" ")[1];
            }
        }
        String param = IOUtils.readData(br, contentLength);
        log.info("param: {}", param);
        User newUser = createUser(param);
        DataBase.addUser(newUser);
        log.info("createUser success");
        path = "/index.html";
        response(out,path,"302");
        return;
    }

    private void response(OutputStream out, String path,String statusCode) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = getBody(path);
        if (statusCode == "302") {
            response302Header(dos, body.length,path);
            dos.flush();
            return;
        }
        else{
            response200Header(dos, body.length);
        }
        responseBody(dos, body);
    }

    private byte[] getBody(String path) throws IOException {
        return Files.readAllBytes(new File("./webapp" + path).toPath());
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, int lengthOfBodyContent,String locationURL) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + locationURL + " \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
