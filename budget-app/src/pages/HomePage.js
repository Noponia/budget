import { Typography, AppBar, Button, Box, Stack, Toolbar } from '@mui/material';
import { Link } from "react-router-dom";

/**
 * HomePage for the user accessed via '/'
 * @returns 
 */
const HomePage  = () => {
    
    const IMAGE_URL = "https://media.istockphoto.com/vectors/cute-piggy-bank-and-coins-drawing-vector-id1292570677?k=20&m=1292570677&s=612x612&w=0&h=a6rD5ut8sRtwGoR0BiyEFOXOqX7vieVnXYF5PIvP-8U="

    return (
        <Box>
            <AppBar position='static' style={{backgroundColor:'#BEBEBE'}}>
                <Toolbar>
                    <Typography variant="h4" style={{flexGrow: 1}}>BUDGETAPP</Typography>
                    <Stack direction='row' spacing={1}>
                        <Button color="inherit" component={Link} to="register">Sign Up</Button>
                        <Button color="inherit" component={Link} to="login">Sign In</Button>
                    </Stack>       
                    </Toolbar>                
            </AppBar>
            <Box 
                display="flex" 
                flexDirection="column">
                <Box alignSelf="center" mt={2}>
                    <img src={IMAGE_URL} alt="Pig" width="600"></img>
                </Box>
                <Typography variant="h4" alignSelf="center" mt={8}>TAKE YOUR BUDGETING TO NEW HEIGHTS</Typography>
            </Box>
        </Box>
    );
}

export default HomePage;