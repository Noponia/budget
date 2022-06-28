import { List, ListItemButton, ListItemText } from '@mui/material';
import { Link } from 'react-router-dom';

/**
 * This component is reused by our dashboard page. It shows the navigable pages on our dashboard on the side.
 * @param {*} props 
 * @returns 
 */
const DashboardSidebar = (props) => {
    return (
        <List>
            <ListItemButton
                selected = {props.selectedItem === 1}
                component={Link} to="/profile"
            >
                <ListItemText> Profile </ListItemText>
            </ListItemButton>
            <ListItemButton
                selected = {props.selectedItem === 2}
                component={Link} to="/transaction"
            >
                <ListItemText> Transaction </ListItemText>
            </ListItemButton>
            <ListItemButton
                selected = {props.selectedItem === 3}
                component={Link} to="/report"
            >
                <ListItemText> Report </ListItemText>
            </ListItemButton>
        </List>
    );
}

export default DashboardSidebar;