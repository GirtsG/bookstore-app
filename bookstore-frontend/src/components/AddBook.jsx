import React, { useState } from 'react';
import axios from 'axios';
import './AddBook.css';

const AddBook = ({ onBookAdded }) => {
    const [name, setName] = useState('');
    const [author, setAuthor] = useState('');
    const [price, setPrice] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const handleAddBook = (e) => {
        e.preventDefault();
        const newBook = { name, author, price: parseFloat(price) };

        const url = `http://localhost:8080/books?name=${encodeURIComponent(newBook.name)}&author=${encodeURIComponent(newBook.author)}&price=${newBook.price}`;

        axios.post(url)
            .then(() => {
                setName('');
                setAuthor('');
                setPrice('');
                setErrorMessage('');

                onBookAdded();
            })
            .catch(error => {
                setErrorMessage(error.response.data || "Error adding book");
            });
    };

    return (
        <div className="form-container">
            <h2>Add New Book</h2>
            <form onSubmit={handleAddBook}>
                <div>
                    <label>Name:</label>
                    <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
                </div>
                <div>
                    <label>Author:</label>
                    <input type="text" value={author} onChange={(e) => setAuthor(e.target.value)} required />
                </div>
                <div>
                    <label>Price:</label>
                    <input type="number" value={price} onChange={(e) => setPrice(e.target.value)} required />
                </div>
                <button type="submit">Add Book</button>
                {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
            </form>
        </div>
    );
};

export default AddBook;
