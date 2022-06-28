import { Chart, Title, Tooltip, LineElement, ArcElement, CategoryScale, LinearScale, PointElement } from 'chart.js';
import { Line, Pie } from 'react-chartjs-2';

Chart.register(Title, Tooltip, LineElement, CategoryScale, LinearScale, PointElement, ArcElement);

/**
 * This component is used to display graphs based on the type of graph selected by the user.
 * Currently there are 2 options supported and they are cumulative line and pie chart.
 * @param {*} props 
 * @returns 
 */
const Graph = (props) => {
    const { graphType, labels, data } = props;      // Need to pass down props that includes the graphType chosen by user
                                                    // 'labels' = horizontal data and 'data' = vertical data

    // Cumulative line shows how much expenses or income has been accumulated over a specified date range
    if(graphType === "Cumulative Line") {
        return (
            <Line
                options = {{
                    scales: {
                        x: {
                            title: {
                                display:true,
                                text: "Date",
                                font : {
                                    size: 16,
                                    weight: "bold"
                                }
                            }
                        },
                        y: {
                            title: {
                                display:true,
                                text: "Amount ($)",
                                font : {
                                    size: 16,
                                    weight: "bold"
                                }
                            }
                        }
                    },
                    plugins: {
                        title: {
                            display:true,
                            text:graphType, 
                            font: {
                                size: 24
                            }
                        }
                    }
                }}
                data = {{
                    // x-axis label values
                    labels: labels,
                    datasets: [
                        {
                            // y-axis data plotting values
                            data: data,
                            borderColor: 'rgb(53, 162, 235)',
                            backgroundColor: 'rgba(53, 162, 235, 0.5)',
                            responsive:true
                        },
                    ],
                }}
            />
            
        );
    }

    // Pie graph shows the distribution of amounts received/spent across different categories.
    else if (graphType === "Pie") {
        return (
            <Pie
                options = {{
                    responsive: true,
                    plugins: {
                        legend: {
                            position: "top"
                        },
                        title: {
                            display:true,
                            text:graphType + " Chart", 
                            font: {
                                size: 24
                            }
                        }
                    }
                }}
                data = {{
                    labels: labels,
                    datasets: [
                        {
                        data: data,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)',
                            'rgba(5, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)',
                            'rgba(5, 159, 64, 1)'

                        ],
                        borderWidth: 1,
                        },
                    ],
                }}
            />
        );
    }
}

export default Graph;