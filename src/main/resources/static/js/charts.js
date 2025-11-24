// Chart utilities for SmartStockAI

// Create sales trend chart
function createSalesTrendChart(canvasId, salesData) {
    const ctx = document.getElementById(canvasId).getContext('2d');
    
    const labels = salesData.map(sale => formatDate(sale.saleDate));
    const data = salesData.map(sale => sale.totalAmount);
    
    return new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Sales Amount',
                data: data,
                borderColor: '#3498db',
                backgroundColor: 'rgba(52, 152, 219, 0.1)',
                tension: 0.4,
                fill: true
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: true,
                    position: 'top'
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return 'Amount: ₹' + context.parsed.y.toFixed(2);
                        }
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function(value) {
                            return '₹' + value;
                        }
                    }
                }
            }
        }
    });
}

// Create stock levels chart
function createStockLevelsChart(canvasId, stockData) {
    const ctx = document.getElementById(canvasId).getContext('2d');
    
    const labels = stockData.map(item => item.productName);
    const currentStock = stockData.map(item => item.quantity);
    const minStock = stockData.map(item => item.minStock);
    
    return new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Current Stock',
                    data: currentStock,
                    backgroundColor: '#27ae60',
                    borderColor: '#229954',
                    borderWidth: 1
                },
                {
                    label: 'Minimum Stock',
                    data: minStock,
                    backgroundColor: '#e74c3c',
                    borderColor: '#c0392b',
                    borderWidth: 1
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: true,
                    position: 'top'
                }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

// Create profit chart
function createProfitChart(canvasId, profitData) {
    const ctx = document.getElementById(canvasId).getContext('2d');
    
    return new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Profit', 'Expenses'],
            datasets: [{
                data: [profitData.profit, profitData.expenses],
                backgroundColor: ['#27ae60', '#e74c3c'],
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'bottom'
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return context.label + ': ₹' + context.parsed.toFixed(2);
                        }
                    }
                }
            }
        }
    });
}

// Create demand forecast chart
function createForecastChart(canvasId, productName, forecastData) {
    const ctx = document.getElementById(canvasId).getContext('2d');
    
    return new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4'],
            datasets: [{
                label: 'Predicted Demand',
                data: forecastData,
                borderColor: '#3498db',
                backgroundColor: 'rgba(52, 152, 219, 0.1)',
                tension: 0.4,
                fill: true
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                title: {
                    display: true,
                    text: `Demand Forecast for ${productName}`
                },
                legend: {
                    display: true,
                    position: 'top'
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1
                    }
                }
            }
        }
    });
}

// Export functions
window.chartUtils = {
    createSalesTrendChart,
    createStockLevelsChart,
    createProfitChart,
    createForecastChart
};
