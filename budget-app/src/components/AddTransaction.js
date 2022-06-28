import { InputAdornment, Typography, Button, Box, TextField, Paper,  Select, MenuItem , InputLabel } from '@mui/material';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import { useState } from 'react';
import { BASE_URL } from '../api/Connections';
import Categories from './Categories';

/**
 * This component allows user to submit a form to add a transaction. Each transaction contains information about
 * the category, description, amount and date.
 * @param {*} props 
 * @returns 
 */
const AddTransaction = (props) => {
    // States for the form submission
    const [transaction, setTransaction] = useState({category:"", description:"", amount:"", date:new Date()});
    const [transactionType, setTransactionType] = useState("");
    const [profileSelect, setProfileSelect] = useState("");

    // We require a list of profiles from props so that user can assign the transaction to a profile
    const {allProfiles} = props;
    
    // This allows users to send a POST request to the backend to add a transaction
    const addTransaction = () => {
        const request = {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem("Bearer") 
            },
            body: JSON.stringify(transaction)
        };
        
        const endpoint = BASE_URL + "/transaction/add-" + transactionType.toLowerCase() +  "/" + profileSelect;
        
        fetch(endpoint, request)
            .catch((err) => console.log(err))
        
        // If the POST request has been successful we will clear the input fields and refresh the page so the changes are reflected in the tables
        clearInputFields();
        window.location.replace("/transaction");
    }

    // This method is responsible for handling submissions by the users
    const handleSubmit = () => {
        // If any of the fields are blank it will alert the user that they're missing inputs
        if(transaction.category === "" ||
            transaction.amount === "" ||
            transaction.date === undefined) {
            alert("Invalid input, try again!")
        } else {
            addTransaction();
        }
    }

    // Function to change the transactionType state once the user has selected a transaction type using the drop down
    const handleSelectTransaction = (e) => {
        setTransactionType(e.target.value);
    }

    // Function to change the profile state once the user has selected a profile using the drop down
    const handleSelectProfile = (e) => {
        setProfileSelect(e.target.value);
    }

    // Function that just clears the input fields once a submission has been made
    const clearInputFields = () => {
        setTransaction({category:"", description:"", amount:"", date:""})
    }
    
    return (
        <Box mt={2} display="block">
            <Paper>
                <Box display="flex" flexDirection="column" rowGap={1}>
                    <Typography variant="h5" ml={2} mb={2}>ADD TRANSACTION</Typography>
                    <InputLabel id="profileLabel">Profile</InputLabel>
                    <Select labelId="profileLabel" onChange={handleSelectProfile}>
                        {allProfiles.map((profile, iter) => (
                            <MenuItem key={iter} value={profile.profileId}>{profile.firstName + " " + profile.lastName}</MenuItem>
                        ))}
                    </Select>
                    <InputLabel id="transactionTypeLabel">Type</InputLabel>
                    <Select 
                        labelId="transactionTypeLabel"
                        value={transactionType}
                        onChange={handleSelectTransaction}
                        >
                        <MenuItem value="Income">Income</MenuItem>
                        <MenuItem value="Expense">Expense</MenuItem>
                    </Select>
                    <InputLabel id="categoryLabel">Categories</InputLabel>
                    <Categories labelId="categoryLabel" transaction={transaction} setTransaction={setTransaction} transactionType={transactionType}/>
                    <TextField
                        label="Description"
                        variant="outlined"
                        value={transaction.description}
                        onChange={(data)=>{setTransaction({...transaction,description:data.target.value})}}
                    />
                    <TextField
                        InputProps={{
                            startAdornment: (
                            <InputAdornment position="start">
                                $
                            </InputAdornment>
                            ),
                      }}
                        label="Amount"
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
                    <Box mb={1} display="flex" justifyContent="center" alignItems="center">
                        <Button variant="outlined" onClick={()=>handleSubmit()}>
                            SUBMIT
                        </Button>
                    </Box>
                </Box>
            </Paper>
        </Box>
    );
}

export default AddTransaction;