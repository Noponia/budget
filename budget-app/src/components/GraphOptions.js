import { Paper, Select, Typography, Box, InputLabel, MenuItem, Button, TextField } from '@mui/material';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import { useEffect } from 'react';

/**
 * This component has 2 purposes:
 * 1) Handles form submission 
 * 2) Once submission has been made it will filter the data to then be displayed on the page
 * @param {*} props 
 * @returns 
 */
const GraphOptions = (props) => {

    const { graphType, setGraphType, transactionType, setTransactionType, allExpense, allIncome,
        startDate, setStartDate, endDate, setEndDate, setLabels, setData } = props;
        

    // When Graph Type changes make sure the data to be displayed is updated
    useEffect(() => {
        generateGraph();
    }, [graphType, transactionType, startDate, endDate])
    
    // Here we need to handle what data to display to user based on what has been requested
    const generateGraph = () => {
        let displayData = []        // Here we create an empty array which will contain data along the vertical axis to be displayed
        let displayLabel = []       // Here we create an empty array which will contain data along the horizontal axis to be displayed

        if(graphType === "Cumulative Line") {
            if(transactionType === "Income") {
                [displayData, displayLabel] = generateCumulativeLineData(allIncome);
            }

            else if(transactionType === "Expense") {
                [displayData, displayLabel] = generateCumulativeLineData(allExpense);
            }
        }

        else if(graphType === "Pie") {
            let result = {};

            if(transactionType === "Income") {
                // Here we filter the data so that it matches the date range specified by the user
                const filteredIncome = allIncome.filter((transaction) => new Date(transaction.date) <= endDate && new Date(transaction.date) >= startDate);     

                result = {'Job':0, 'Gift':0, 'Investment':0} // This is dictionary that contains the amount accumulated for each category of income

                filteredIncome.forEach((income) => {        // Here we loop through the filtered income (by date range) and then add the amounts to the approrpriate category
                    result[income.category] += income.amount;
                })
            } else if(transactionType === "Expense") {
                const filteredExpense = allExpense.filter((transaction) => new Date(transaction.date) <= endDate && new Date(transaction.date) >= startDate); 

                result = {'Bill':0, 'Clothes':0, 'Entertainment':0, 'Food':0, 'Groceries':0, 'Travel':0, 'Other':0}

                filteredExpense.forEach((expense) => {
                    result[expense.category] += expense.amount;
                })
            }
            displayLabel = Object.keys(result);             // The categories represents the keys of the result dictionary
            displayData = Object.values(result);            // The total amount $ represents the values of the result dictionary
        }

        setLabels(displayLabel);
        setData(displayData);
    }

    // This function will sort the transactions by their date in ascending order and then filter it by the range of dates selected by the user.
    // The filtered and sorted data becomes the data to be represented by the cumulative line graph.
    const generateCumulativeLineData = (transactions) => {
        let displayData = []
        let displayLabel = []
        
        // This will sort the income transactions by date and then filter within the user's specified range
        const filteredTransactions = transactions.sort((a, b) => {return new Date(a.date) - new Date(b.date)})
                                        .filter((transaction) => new Date(transaction.date) <= endDate && new Date(transaction.date) >= startDate)

        // Here we create an array that contains the amounts only for each transaction
        const transactionAmounts = filteredTransactions.map((transaction) => transaction.amount);
         

        // This for loop is used to build our cumulative line graph. 
        for(let i = 0; i < filteredTransactions.length; i++) {
            if(i === 0) {
                displayData.push(transactionAmounts[i]);
            } else {
                displayData.push(transactionAmounts[i] + displayData[i - 1]);
            }

            displayLabel.push(filteredTransactions[i].date);
        }

        return [displayData, displayLabel]
    }

    return (
        <Paper>
            <Box display="flex" flexDirection="column" rowGap={2}>
                <Typography variant="h5">GRAPH OPTIONS</Typography>
                <InputLabel id="graphTypeLabel">Graph Type</InputLabel>
                <Select 
                    labelId="graphTypeLabel"
                    value={graphType}
                    onChange={(e) => {setGraphType(e.target.value)}}
                    >
                    <MenuItem value="Cumulative Line">Cumulative Line</MenuItem>
                    <MenuItem value="Pie">Pie</MenuItem>
                </Select>
                <InputLabel id="transactionTypeLabel">Transaction Type</InputLabel>
                <Select 
                    labelId="transactionTypeLabel"
                    value={transactionType}
                    onChange={(e) => {setTransactionType(e.target.value)}}
                    >
                    <MenuItem value="Income">Income</MenuItem>
                    <MenuItem value="Expense">Expense</MenuItem>
                </Select>
                <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <DesktopDatePicker
                        label="Start Date"
                        inputFormat="yyyy/MM/dd"
                        value={startDate}
                        onChange={(data)=>{setStartDate(data)}}
                        renderInput={(params) => <TextField {...params} />}
                    />
                </LocalizationProvider>
                <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <DesktopDatePicker
                        label="End Date"
                        inputFormat="yyyy/MM/dd"
                        value={endDate}
                        onChange={(data)=>{setEndDate(data)}}
                        renderInput={(params) => <TextField {...params} />}
                    />
                </LocalizationProvider>
            </Box>
        </Paper>
    );
}

export default GraphOptions;