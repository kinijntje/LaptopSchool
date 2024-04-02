const express = require('express');
const bcrypt = require('bcrypt');
const cors = require('cors');

const app = express();
app.use(express.json());
app.use(cors()); // Enable CORS for all routes

const users = {};

app.post('/register', (req, res) => {
  const { username, password } = req.body;

  if (users[username]) {
    return res.status(400).json({ error: 'Username already exists' });
  }

  bcrypt.hash(password, 10, (err, hash) => {
    if (err) {
      return res.status(500).json({ error: 'Failed to register user' });
    }

    users[username] = { username, password: hash };
    console.log("users: ", users)
    res.status(200).json({ message: 'User registered successfully', "username": username });
  });
});

app.post('/login', (req, res) => {
  const { username, password } = req.body;
  const user = users[username];

  if (!user) {
    return res.status(401).json({ error: 'Username does not exist' });
  }

  bcrypt.compare(password, user.password, (err, result) => {
    if (err || !result) {
      return res.status(401).json({ error: 'Invalid username or password' });
    }

    res.status(200).json({ message: 'User logged in successfully', "username": username });
  });
});

app.listen(3000, () => {
  console.log('Server started on port 3000');
});
