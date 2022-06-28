import { useEffect, useState } from 'react';
import { BASE_URL } from '../api/Connections';
import { Alert, Typography, Grid, TextField, Button, Box } from '@mui/material';
import { Link } from "react-router-dom";

/**
 * This page is used to register a user
 * @returns 
 */
const RegisterPage = () => {
    const [newUser, setNewUser] = useState({username:"", password:""});     // State to keep track of user input in the username/password fields
    const [displayError, setDisplayError] = useState({username: false, password: false});       // State to track if an error message should be displayed to the user
    const [errorMessage, setErrorMessage] = useState({username: "", password: ""});     // State that determines what error message to display to the user
    const [alert, setAlert] = useState(false);      // State that determines if an alert message should be displayed once an unsuccessful login has been attempted

    const IMG_URL = "https://cdn.create.vista.com/api/media/small/323505412/stock-photo-silver-coins-piggy-bank-light";

    // If a display error has been displayed upon invalid input we need to remove the error message once the user types on their keyboard
    useEffect(() => {
        setDisplayError({username: false, password: false});
        setErrorMessage({username: "", password: ""});
    }, [newUser])
    
    // Make a POST request once the user clicks the register button
    const registerUser = () => {
        const request = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newUser)
        };

        const endpoint = BASE_URL + "/register"

        fetch(endpoint, request)
            .then((response) => handleResponse(response))
            .catch((error) => console.log("Error: ", error))
    }

    // Clears the input fields in the register page once the user has submitted
    const handleResponse = (response) => {
        if(response.status === 200) {
            setNewUser({username:"", password:""})
            setAlert(true);
        } else {
            setErrorMessage({...errorMessage, username:"Username taken!"})
            setDisplayError({...displayError, username: true});
        }
    }

    // Handles submit button, we post the request to the database only if the username/password inputs are valid
    const handleSubmit = () => {
        if(validate()) {
            registerUser();
        } 
    }

    // Function to validate the username/password inputs, currently validation is that the fields must not be blank
    const validate = () => {
        let numFailedChecks = 0;
        
        if(newUser.username === "") {
            setErrorMessage({...errorMessage, username:"Password must not be empty!"});
            setDisplayError({...displayError, username: true});
            numFailedChecks += 1;
        } 
        
        if(newUser.password === "") {
            setErrorMessage({...errorMessage, password:"Password must not be empty!"});
            setDisplayError({...displayError, password: true});
            numFailedChecks += 1;
        }

        if(numFailedChecks > 0) {
            setAlert(false);
            return false;
        } else {
            return true;
        }
    }

    return (
        <Grid container alignItems="center" justifyContent="center">
            <Grid item xs={6}>
                <img
                    src={IMG_URL}
                    style={{ minWidth:"50vw", maxHeight:"100vh", objectFit:"cover"}}
                    alt="Piggy Bank"
                ></img>
            </Grid>
            <Grid container item alignItems="center" direction="column" xs={6}>
                <Box 
                    display="flex"
                    gap="10px"
                    alignItems="center" 
                    flexDirection="column" 
                    justify="center">
                    <Typography variant="h5">
                        REGISTER
                    </Typography>
                    {
                        alert 
                        ? <Alert onClose={()=>{setAlert(false)}} severity="success">Registration Success!</Alert>
                        : <></>
                    }
                    <TextField
                        label="Username"
                        error={displayError.username}
                        helperText={errorMessage.username}
                        value={newUser.username}
                        onChange={(data)=>{setNewUser({...newUser,username:data.target.value})}}
                    />
                    <TextField
                        label="Password"
                        error={displayError.password}
                        helperText={errorMessage.password}
                        value={newUser.password}
                        onChange={(data)=>{setNewUser({...newUser,password:data.target.value})}}
                    />
                    <Button variant="contained" onClick={()=>handleSubmit()}>
                        Sign up
                    </Button>
                    <Box>
                        <Button variant="text" component={Link} to="/">
                            Home
                        </Button>
                        <Button variant="text" component={Link} to="/login">
                            Login
                        </Button>
                    </Box>
                </Box>
               
            </Grid>
        </Grid>
    
    );
}

export default RegisterPage;
    