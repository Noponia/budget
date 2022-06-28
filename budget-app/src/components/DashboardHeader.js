import { AppBar, Toolbar, Typography, Button } from '@mui/material';
import SavingsIcon from '@mui/icons-material/Savings';

/**
 * This is a component used by our dashboard pages that shows a top bar for the page.
 * @returns 
 */
const DashboardHeader = () => {

    // Here we handle logout feature of the application
    const logout = () => {

        // Here we delete their token and take them back to the homepage.
        localStorage.clear();
        window.location.replace("/");
    
    }

    return (
        <AppBar position='static'>
            <Toolbar>
                <SavingsIcon />
                <Typography variant="h4" style={{flexGrow: 1}}>BUDGETAPP</Typography>
                <Button color="inherit" onClick={() => logout()}>Log Out</Button>
            </Toolbar>
        </AppBar>
    );
}

export default DashboardHeader;