import {  Box } from '@mui/material';
import { useEffect, useState } from 'react';
import DashboardHeader from '../components/DashboardHeader';
import DashboardSidebar from '../components/DashboardSidebar';
import Profiles from '../components/Profiles';
import GraphOptions from '../components/GraphOptions';
import Graph from '../components/Graph';

import { BASE_URL } from '../api/Connections';

/**
 * This page is to display graphs to the user
 * @returns 
 */
const ReportPage = () => {

    // These are all states for the form
    const [graphType, setGraphType] = useState("")
    const [transactionType, setTransactionType] = useState("");
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());

    // Thesee are all incomes/expenses/profiles underneath a user, pulled from the backend once only
    const [allIncome, setAllIncome] = useState([]);
    const [allExpense, setAllExpense] = useState([]);
    const [allProfiles, setAllProfiles] = useState([]);

    // Labels refer to the ROW data and data refers to the COLUMN data
    const [labels, setLabels] = useState([]);
    const [data, setData] = useState([]);

    // Upon loading the ReportPage, pull all income and expense transactions from the database once
    useEffect(() => {
        getAllExpenseTransactions();
        getAllIncomeTransactions();
    }, [])

    // Get request for getting all income transactions
    const getAllIncomeTransactions = () => {
        const request = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json', 
                'Authorization': localStorage.getItem("Bearer")
            }
        }

        const endpoint = BASE_URL + "/transaction/all-income";

        fetch(endpoint, request)
            .then(response => response.json())
            .then(data => setAllIncome(data))
            .catch((err) => console.log(err))
    }

    // Get request for getting all expense transactions
    const getAllExpenseTransactions = () => {
        const request = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json', 
                'Authorization': localStorage.getItem("Bearer")
            }
        }

        const endpoint = BASE_URL + "/transaction/all-expense";

        fetch(endpoint, request)
            .then(response => response.json())
            .then(data => setAllExpense(data))
            .catch((err) => console.log(err))
    }

    return (
        <Box>
            <DashboardHeader />
            <Box display="flex" ml={2} mt={2} justifyContent="top">
                <Box>
                    <DashboardSidebar selectedItem={3} />
                </Box>
                <Box ml={2}>
                    <Profiles allProfiles={allProfiles} setAllProfiles={setAllProfiles} />
                    <Box mt={2}>
                        <GraphOptions
                            graphType={graphType}
                            setGraphType={setGraphType}
                            transactionType={transactionType}
                            setTransactionType={setTransactionType}
                            allIncome={allIncome}
                            allExpense={allExpense}
                            startDate={startDate}
                            setStartDate={setStartDate}
                            endDate={endDate}
                            setEndDate={setEndDate}
                            setLabels={setLabels}
                            setData={setData}
                        />
                    </Box>
                </Box>
                <Box ml={10} mt={2} width="900px">
                    <Graph 
                        graphType={graphType}
                        labels={labels}
                        data={data}
                    />
                </Box>
            </Box>
        </Box>
    );
}

export default ReportPage;