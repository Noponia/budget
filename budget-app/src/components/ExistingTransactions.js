import { Typography, Button, Box, Paper, TableContainer, Table, 
    TableHead, TableCell, TableRow, TableBody, TablePagination} from '@mui/material';
import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { BASE_URL } from '../api/Connections';

/**
 * This component is responsible for showing a table of all the transactions made by the user.
 * @returns 
 */
const ExistingTransactions = () => {
    const [transactions, setTransactions] = useState([]);           // This contains all the transactions made by the user
    const [page, setPage] = useState(0);                            // This is for pagination, it determines what pages the user beings at
    const [rowsPerPage, setRowsPerPage] = useState(10);             // This is for pagination, it determines how many rows to be displayed per page

    // We pull all the transactions from the database once when we load into the page
    useEffect(() => {
        getAllTransactions();  
    }, [])

    /**
     * Returns all the transactions made by 1 profile
     * Calls /api/transaction/all/{profileId}
     * @param {number} profileId 
     */
    const getAllTransactions= () => {
        const request = {
            method: 'GET',
            headers: { 
                'Content-Type': 'application/json', 
                'Authorization': localStorage.getItem("Bearer")
            }
        };

        const endpoint = BASE_URL + "/transaction/all";

        fetch(endpoint, request)
            .then(response => response.json())
            .then(data => setTransactions(data))
            .catch((err) => console.log(err))
    }

    /**
     * Deletes a transaction using its ID
     * @param {number} transactionId 
     */
    const handleDelete = (transactionId) => {
        const request = {
            method: 'DELETE',
            headers: { 
                'Content-Type': 'application/json', 
                'Authorization': localStorage.getItem("Bearer")
            },
        };

        const endpoint = BASE_URL + "/transaction/delete/" + transactionId;

        fetch(endpoint, request)
            .catch((err) => console.log(err))
        
        window.location.replace('/transaction')
    }

    // Use for pagination to handle user wanting to move between pages
    const handleChangePage = (event, newPage) => {
        setPage(newPage);
      };
    

    // Use for pagination to handle user wanting to change the number of rows per page
    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    return (
        <Box ml={2}>
            <TableContainer component={Paper}>
                <Typography variant="h5" ml={2}>EXISTING TRANSACTIONS</Typography>
                <Table aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell align="center">Profile ID</TableCell>
                            <TableCell align="center">Transaction ID</TableCell>
                            <TableCell align="center">Type</TableCell>
                            <TableCell align="center">Category</TableCell>
                            <TableCell align="center">Description</TableCell>
                            <TableCell align="center">Amount</TableCell>
                            <TableCell align="center">Date</TableCell>
                            <TableCell align="center"></TableCell>
                            <TableCell align="center"></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                    {transactions.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((transaction) => (
                        <TableRow
                            key={transaction.transactionId}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell align="center">{transaction.profile.profileId}</TableCell>
                            <TableCell align="center">{transaction.transactionId} </TableCell>
                            <TableCell align="center">{transaction.type}</TableCell>
                            <TableCell align="center">{transaction.category}</TableCell>
                            <TableCell align="center">{transaction.description}</TableCell>
                            <TableCell align="center">{"$"  + transaction.amount}</TableCell>
                            <TableCell align="center">{transaction.date}</TableCell>
                            <TableCell>
                                <Button component={Link} to={"/edit/?transactionId=" + transaction.transactionId}>EDIT</Button>
                            </TableCell>
                            <TableCell>
                                <Button onClick={() => handleDelete(transaction.transactionId)}>DELETE</Button>
                            </TableCell>
                        </TableRow>
                    ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                component="div"
                count={transactions.length}
                page={page}
                onPageChange={handleChangePage}
                rowsPerPage={rowsPerPage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </Box>
    );
}

export default ExistingTransactions;