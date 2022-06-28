import { MenuItem, Select } from '@mui/material'

/**
 * This component is responsible for determining what categories to display when the user wants to add a Transaction based on the transaction type selected.
 * @param {*} props 
 * @returns 
 */
const Categories = (props) => {

    /** 
     * We need to pass down props to Categories containing information about the transaction and transactionType. We also need a setTransaction prop to change
     * the category being shown to the user when the user has made their selection.
     */
    
    const{transaction, setTransaction, transactionType} = props;

    /**
     * Categories currently supported underneath expense are: Bill, Clothes, Entertainment, Food, Groceries, Travel and Other
     */
    if(transactionType === "Expense") {
        return (
            <Select
            labelId="categoryLabel"
            value={transaction["category"]}
            onChange={(data)=>{setTransaction({...transaction,category:data.target.value})}} >
                <MenuItem value="Bill">Bill</MenuItem>
                <MenuItem value="Clothes">Clothes</MenuItem>
                <MenuItem value="Entertainment">Entertainment</MenuItem>
                <MenuItem value="Food">Food</MenuItem>
                <MenuItem value="Groceries">Groceries</MenuItem>
                <MenuItem value="Travel">Travel</MenuItem>
                <MenuItem value="Other">Other</MenuItem>
            </Select>
        )
    }

    /**
     * Categories currently supported underneath income are: Job, Gift and Investment
     */
    else if(transactionType === "Income") {
        return (
            <Select 
            labelId="categoryLabel"
            value={transaction["category"]}
            onChange={(data)=>{setTransaction({...transaction,category:data.target.value})}} >
                <MenuItem value="Job">Job</MenuItem>
                <MenuItem value="Gift">Gift</MenuItem>
                <MenuItem value="Investment">Investment</MenuItem>
            </Select>
        )
    }

    /**
     * If no transactionType has been selected we don't display any selectable options to the user.
     */
    else {
        return (
            <Select 
            labelId="categoryLabel"
            value={transaction["category"]} >
                <MenuItem></MenuItem>
            </Select>
        )
    }
}

export default Categories;