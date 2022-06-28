import { Box, Grid } from '@mui/material';
import { useState } from 'react';
import DashboardHeader from '../components/DashboardHeader';
import DashboardSidebar from '../components/DashboardSidebar';
import Profiles from '../components/Profiles'
import AddTransaction from '../components/AddTransaction';
import ExistingTransactions from '../components/ExistingTransactions';

/**
 * This page is used to diplay/add/edit/update transactions
 * @returns 
 */
const TransactionPage = () => {

    // State for all the profiles registered underneath a user
    const [allProfiles, setAllProfiles] = useState([]);

    return (
        <Box>
            <DashboardHeader />
            <Box display="flex" ml={2} mt={2} justifyContent="top">
                <Box>
                    <DashboardSidebar selectedItem={2} />
                </Box>
                <Grid container ml={2}>
                    <Grid item xs={3}>
                        <Profiles allProfiles={allProfiles} setAllProfiles={setAllProfiles} />
                        <AddTransaction allProfiles={allProfiles} />
                    </Grid>
                    <Grid item xs={7}>
                        <ExistingTransactions /> 
                    </Grid>
                </Grid>
            </Box> 
        </Box>
    );
}

export default TransactionPage;