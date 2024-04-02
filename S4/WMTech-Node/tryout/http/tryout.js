const http = require("http");
const fs = require("fs");

function requestHandler(req, res) {
    console.log("request", req.url);
    if(req.url.endsWith(".html")) {
        fs.readFile("./www/index.html", "utf-8", (err, html) => {
            res.setHeader("Content-Type", "text/html");
            res.end(html);
        })
    } else if(req.url.endsWith(".css")) {
        fs.readFile("./www/assets/style.css", "utf-8", (err, html) => {
            res.setHeader("Content-Type", "text/css");
            res.end(html);
        })
    } else {
        res.statusCode = 404;
        res.end("not found");
    }
}

const server = http.createServer(requestHandler);
server.listen(8080, err =>{
    if (err) return console.error("Error", err);
    console.log("Listening at ", 8080);
})