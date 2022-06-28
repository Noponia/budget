import { Typography, Button, Box, TextField, Paper, TableContainer, Table, TableHead, TableCell, TableRow, TableBody } from '@mui/material';
import { useEffect, useState } from 'react';
import { BASE_URL } from '../api/Connections';
import DashboardHeader from '../components/DashboardHeader';
import DashboardSidebar from '../components/DashboardSidebar';

/**
 * Page that shows all the profiles registered underneath a user. They can also add/delete a profile from this page, accessed via '/profile'
 * @returns 
 */
const ProfilePage = () => {

    const [profile, setProfile] = useState({firstName:"", lastName:""});
    const [existProfiles, setExistProfiles] = useState([]);
    const [showAddProfile, setShowAddProfile] = useState(false);

    // When the profile page is loaded we will from the database all the profiles once
    useEffect(()=> {
        getProfiles();
    }, [])

    // When the number of profiles has changed it signifies that user has added a profile, so we will clear the input fields once done so
    useEffect(() => {
        clearFields();
    }, [existProfiles.length])

    const IMG_URL = "https://i.pinimg.com/originals/81/d2/00/81d200d48f6d13e1c132ecf37b7606b1.gif";

    // Method to fetch all the profiles under a user from the database
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
            .then(data => setExistProfiles(data))
            .catch((err) => console.log(err))
    }

    // Method to delete a profile based on its profileId
    const deleteProfile = (profileId) => {
        const request = {
            method: 'DELETE',
            headers: { 
                'Content-Type': 'application/json', 
                'Authorization': localStorage.getItem("Bearer")
            },
        };

        const endpoint = BASE_URL + "/profile/delete/" + profileId;

        fetch(endpoint, request)
            .catch((err) => console.log(err))
        
       window.location.replace('/profile') // Refresh the page so that the table of existing profiles are updated
    }
    
    // Method to  add a profile to the database
    const addProfile = () => {
        const request = {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json', 
                'Authorization': localStorage.getItem("Bearer")
            },
            body: JSON.stringify(profile)
        };

        const endpoint = BASE_URL + "/profile/add";

        fetch(endpoint, request)
            .catch((err) => console.log(err))
        
        window.location.replace('/profile')
    }

    // When the user presses the submit button, the event will be handled using this method
    const handleAddProfile = () => {
        // Here we check the input fields aren't empty
        if(profile.firstName === "" || profile.lastName === "") {
            alert("Invalid Profile")
        } else {
            setExistProfiles([...existProfiles, {firstName: profile.firstName, lastName: profile.lastName}])
            addProfile();
        }
    }

    // Method to handle deleteing a profile
    const handleDeleteProfile = (e) => {
        deleteProfile(e.target.value);
    }

    // Method to clear the input fields
    const clearFields = () => {
        setProfile({firstName:"", lastName:""})
    }

    return (
        <Box>
            <DashboardHeader />
            <Box display="flex" ml={2} mt={2}  minHeight="100vh">
                <Box>
                    <DashboardSidebar selectedItem={1}/>
                </Box>
                <Box ml={40} mt={5} flexDirection="column" display="flex">
                    <TableContainer component={Paper}>
                        <Typography variant="h5" ml={2}>EXISTING PROFILES</Typography>
                        <Table aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell align="center">First Name</TableCell>
                                    <TableCell align="center">Last Name</TableCell>
                                    <TableCell align="center"></TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                            {existProfiles.map((profile, iter) => (
                                <TableRow
                                    key={iter}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell align="center">{profile.firstName}</TableCell>
                                    <TableCell align="center">{profile.lastName}</TableCell>
                                    <TableCell align="center">
                                        <Button value={profile.profileId} onClick={(e) => handleDeleteProfile(e)}>DELETE</Button>
                                    </TableCell>
                                </TableRow>
                            ))}
                                <TableRow>
                                    <TableCell>
                                        {
                                          showAddProfile ? (
                                            <TextField
                                                label="First name"
                                                variant="standard"
                                                value={profile.firstName}
                                                onChange={(data)=>{setProfile({...profile,firstName:data.target.value})}}
                                            />
                                          ) : (<></>)
                                        }
                                    </TableCell>
                                    <TableCell>
                                        {
                                          showAddProfile ? (
                                            <TextField
                                                label="Last name"
                                                variant="standard"
                                                value={profile.lastName}
                                                onChange={(data)=>{setProfile({...profile,lastName:data.target.value})}}
                                            />
                                          ) : (<></>)
                                        }
                                    </TableCell>
                                    <TableCell>
                                        {
                                            showAddProfile ? (
                                                <Button variant="outlined" onClick={()=>handleAddProfile()}>
                                                    SUBMIT 
                                                </Button>
                                            ) : (
                                                <Button variant="outlined" onClick={()=>setShowAddProfile(true)}>
                                                    ADD 
                                                </Button>
                                            )
                                        }
                                    </TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Box>
                <Box ml={3}>
                    <img src={IMG_URL} height="450px" alt="Pig"></img>
                </Box>
            </Box>
            
        </Box>
    );
}

export default ProfilePage;