import { useEffect } from 'react'
import { Typography, Paper, TableContainer, Table, TableHead, TableCell, TableRow, TableBody} from '@mui/material';
import { BASE_URL } from '../api/Connections';

/**
 * This component is used to display a table of existing profiles registered under a user
 * @param {*} props 
 * @returns 
 */
const Profiles = (props) => {
    
    // Here we pass down props that contain states allProfiles and setAllProfiles. All profiles contains all the profiles registered under a user.
    const {allProfiles, setAllProfiles} = props;

    useEffect (() => {
        getProfiles();
    }, [])

    // Here we fetch all the profiles from our database.
    const getProfiles = () => {
        const request = {
            method: 'GET',
            headers: { 
                'Content-Type': 'application/json', 
                'Authorization': localStorage.getItem("Bearer")
            }
        };

        const endpoint = BASE_URL + "/profile/all";

        fetch(endpoint, request)
            .then(response => response.json())
            .then(data => setAllProfiles(data))
            .catch((err) => console.log(err))
    }

    return (
        <TableContainer component={Paper}>
            <Typography variant="h5" ml={2}>PROFILES</Typography>
            <Table aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Profile ID</TableCell>
                        <TableCell align="center">First name</TableCell>
                        <TableCell align="center">Last name</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                {allProfiles.map((profile) => (
                    <TableRow
                        key={profile.profileId}
                        sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                        <TableCell align="center" component="th" scope="row">
                            {profile.profileId}
                        </TableCell>
                        <TableCell align="center">{profile.firstName}</TableCell>
                        <TableCell align="center">{profile.lastName}</TableCell>
                    </TableRow>
                ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}

export default Profiles;