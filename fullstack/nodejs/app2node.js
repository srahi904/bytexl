const express = require('express');
const app = express();
const PORT = 3000;

// Middleware to parse JSON bodies
app.use(express.json());

// In-memory cards array for simplicity
let cards = [
  { id: 1, suit: 'Hearts', value: 'Ace' },
  { id: 2, suit: 'Spades', value: 'King' },
  { id: 3, suit: 'Diamonds', value: 'Queen' }
];
let nextId = 4;

// Get all cards
app.get('/cards', (req, res) => {
  res.status(200).json(cards);
});

// Get a card by ID
app.get('/cards/:id', (req, res) => {
  const card = cards.find(c => c.id === Number(req.params.id));
  if (!card) {
    return res.status(404).json({ error: 'Card not found' });
  }
  res.status(200).json(card);
});

// Add a new card
app.post('/cards', (req, res) => {
  const { suit, value } = req.body;
  if (!suit || !value) {
    return res.status(400).json({ error: 'Suit and value are required' });
  }
  const card = { id: nextId++, suit, value };
  cards.push(card);
  res.status(201).json(card);
});

// Delete a card by ID
app.delete('/cards/:id', (req, res) => {
  const idx = cards.findIndex(c => c.id === Number(req.params.id));
  if (idx === -1) {
    return res.status(404).json({ error: 'Card not found' });
  }
  const removed = cards.splice(idx, 1)[0];
  res.status(200).json({
    message: `Card with ID ${removed.id} removed`,
    card: removed
  });
});

// Start the server
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
