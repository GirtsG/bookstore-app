import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AddBook from './AddBook';

const BookList = () => {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [noMoreBooks, setNoMoreBooks] = useState(false);
    const [page, setPage] = useState(0);

    useEffect(() => {
        fetchBooks();
    }, [page]);

    const fetchBooks = () => {
        setLoading(true);
        axios.get(`http://localhost:8080/books?page=${page}&size=50`)
            .then(response => {
                if (Array.isArray(response.data)) {
                    if (response.data.length === 0) {
                        setNoMoreBooks(true);
                    } else {
                        setBooks(prevBooks => {
                            const existingIds = new Set(prevBooks.map(book => book.id));
                            const newBooks = response.data.filter(book => !existingIds.has(book.id));
                            return [...prevBooks, ...newBooks];
                        });
                    }
                } else {
                    setError("Invalid response format");
                }
                setLoading(false);
            })
            .catch(error => {
                setError("Error fetching books: " + error.message);
                setLoading(false);
            });
    };

    const loadMore = () => {
        if (!noMoreBooks) {
            setPage(prevPage => prevPage + 1);
        }
    };

    const handleBookAdded = () => {
        setNoMoreBooks(false);
        setPage(0);
        fetchBooks();
    };

    const formatLatvianDate = (dateArray) => {
        const date = new Date(
            dateArray[0], // Year
            dateArray[1] - 1, // Month (0-indexed)
            dateArray[2], // Day
            dateArray[3], // Hours
            dateArray[4], // Minutes
            dateArray[5] // Seconds
        );
        return new Intl.DateTimeFormat('lv-LV', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit',
            hour12: false
        }).format(date);
    };

    if (loading) return <div>Loading books...</div>;
    if (error) return <div style={{ color: 'red' }}>{error}</div>;

    return (
        <div>
            <h2>Book List</h2>
            <AddBook onBookAdded={handleBookAdded} />
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Author</th>
                    <th>Price</th>
                    <th>Created At</th>
                </tr>
                </thead>
                <tbody>
                {books.length > 0 ? (
                    books.map(book => (
                        <tr key={book.id}>
                            <td>{book.name}</td>
                            <td>{book.author}</td>
                            <td>{book.price.toFixed(2)}</td>
                            <td>{formatLatvianDate(book.createdAt)}</td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan="4">No books available.</td>
                    </tr>
                )}
                </tbody>
            </table>
            <button onClick={loadMore} style={{ display: 'block' }}>Load More</button>
            {noMoreBooks && <p>No more books to load.</p>}
        </div>
    );
};

export default BookList;
