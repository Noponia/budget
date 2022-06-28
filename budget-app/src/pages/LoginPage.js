import { useState } from 'react';
import { BASE_URL } from '../api/Connections';
import { Alert, Typography, Grid, TextField, Button, Box } from '@mui/material';
import { Link } from "react-router-dom";

/**
 * Login page to allow users to login, accessed via '/login'.
 * @returns 
 */
const LoginPage = () => {
    const [login, setLogin] = useState({username:"", password:""});     // State to keep tract of user input for username/password
    const [alert, setAlert] = useState(false);  // State to check if the user needs to be alerted if they failed a login

    const IMG_URL = "https://cdn.create.vista.com/api/media/small/323178752/stock-photo-piggy-bank-isolated-pink-blue";

    // Method to handle user submission of login details
    const handleSubmit = () => {
        const request = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(login)
        };
        
        const endpoint = BASE_URL + "/login"

        fetch(endpoint, request)
            .then(response => loginResponse(response))
            .catch((err) => console.log(err))
    }

    const loginResponse = (response) => {
        // If the user has logged in succesful they will be redirected to the dashboard page
        if(response.status === 200) {
            localStorage.setItem("Bearer", response.headers.get("Authorization"))
            window.location.replace("/profile")
        } else {
            setAlert(true)  // If the user failed their login they will be given an alert message
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
                        SIGN IN
                    </Typography>
                    {
                        alert 
                        ? <Alert onClose={()=>{setAlert(false)}} severity="error">Login Failed!</Alert>
                        : <></>
                    }
                    <TextField
                        label="Username"
                        value={login.username}
                        onChange={(data)=>{setLogin({...login,username:data.target.value})}}
                    />
                    <TextField
                        label="Password"
                        value={login.password}
                        onChange={(data)=>{setLogin({...login,password:data.target.value})}}
                    />
                    <Button variant="contained" onClick={()=>handleSubmit()}>
                        Login
                    </Button>
                    <Box>
                        <Button variant="text" component={Link} to="/register">
                            Register
                        </Button>
                        <Button variant="text" component={Link} to="/">
                            Home
                        </Button>
                    </Box>
                </Box>
               
            </Grid>
        </Grid>
    
    );
}

export default LoginPage;
    