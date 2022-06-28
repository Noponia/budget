import { Typography, Box, Button, Paper, InputLabel, InputAdornment, TextField } from '@mui/material';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import DashboardHeader from '../components/DashboardHeader';
import DashboardSidebar from '../components/DashboardSidebar';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import { BASE_URL } from '../api/Connections';
import Categories from '../components/Categories';

/**
 * This is our EditPage which allows users to edit transactions that they've made. Accessed via '/edit'.
 * @returns 
 */
const EditPage = () => {
    // Here we look at the url to see which transaction needs to be updated
    const search = useLocation().search;
    const transactionId = new URLSearchParams(search).get('transactionId')      // Store the transactionId needing to be updated here

    // States for our for form submission
    const [transaction, setTransaction] = useState({type:"", category:"", description:"", amount:"", date:new Date()});

    const IMG_URL = "https://i.pinimg.com/originals/81/d2/00/81d200d48f6d13e1c132ecf37b7606b1.gif";

    // Here we load data about our transaction being edited once.
    useEffect(() => {
        getTransactionById(transactionId);
    },[]);

    // Method to fetch the transaction by using its Id.
    const getTransactionById = (transactionId) => {
        const request = {
               method: 'GET',
            headers: { 
                'Content-Type': 'application/json', 
                'Authorization': localStorage.getItem("Bearer")
            }
        };

        const endpoint = BASE_URL + "/transaction/" + transactionId;

        fetch(endpoint, request)
            .then(response => response.json())
            .then(data => setTransaction(data))
            .catch((err) => console.log(err))
    }
    
    // Method to update transaction.
    const updateTransaction = () => {
        const request = {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem("Bearer") 
            },
            body: JSON.stringify(transaction)
        };
        
        const endpoint = BASE_URL + "/transaction/update/" + transactionId;
        
        fetch(endpoint, request)
            .catch((err) => console.log(err))
        
        window.location.replace("/transaction");
    }

    return (
        <Box>
            <DashboardHeader />
            <Box display="flex" ml={2} mt={2}>
                <Box>
                    <DashboardSidebar selectedItem={2} />
                </Box>
                <Box ml={40} mt={5}>
                    <Paper>
                        <Box display="flex" flexDirection="column" rowGap={1}>
                            <Typography alignSelf="center" variant="h3">Update</Typography>
                            <InputLabel id="categoryLabel">Categories</InputLabel>
                            <Categories labelId="categoryLabel" transaction={transaction} setTransaction={setTransaction} transactionType={transaction.type}/>
                            <TextField
                                label="Description"
                                variant="outlined"
                                value={transaction.description}
                                onChange={(data)=>{setTransaction({...transaction,description:data.target.value})}}
                            />
                            <TextField
                                label="Amount"
                                InputProps = {{
                                    startAdornment: (
                                        <InputAdornment position="start">
                                            $
                                        </InputAdornment>
                                    ),
                                }}
                                variant="outlined"
                                type="number"
                                value={transaction.amount}
                                onChange={(data)=>{setTransaction({...transaction,amount:data.target.value})}}
                            />
                            <LocalizationProvider dateAdapter={AdapterDateFns}>
                                <DesktopDatePicker
                                    label="Date"
                                    inputFormat="yyyy/MM/dd"
                                    value={transaction.date}
                                    onChange={(data)=>{setTransaction({...transaction,date:data})}}
                                    renderInput={(params) => <TextField {...params} />}
                                />
                            </LocalizationProvider>
                            <Button onClick={() => updateTransaction()}>UPDATE</Button>
                            <Button onClick={() => window.location.replace('/transaction')}>CANCEL</Button>
                        </Box>
                    </Paper>
                </Box>
                <Box ml={3}>
                    <img src={IMG_URL} height="450px" alt="Pig"></img>
                </Box>
            </Box>
        </Box>
    );
}

export default EditPage;