// Enhanced Analytics for Owner Dashboard

let analyticsCharts = {};

async function loadEnhancedAnalytics() {
    try {
        const [products, sales, stock, stockTransactions] = await Promise.all([
            apiService.getProducts(),
            apiService.getSales(),
            apiService.getStock(),
            apiService.getStockTransactions().catch(() => [])
        ]);

        renderFinancialReportTable(sales, stockTransactions, products);
        renderTopProductsChart(products, sales);
        renderSalesTrendChart(sales);
        renderProfitMarginChart(products, sales);
        renderRevenueProfitChart(sales, products);
        renderPerformanceTable(products, sales, stock);
    } catch (error) {
        console.error('Failed to load analytics:', error);
    }
}

function renderFinancialReportTable(sales, stockTransactions, products) {
    try {
        // Get all unique dates from sales and transactions
        const allDates = new Set();
        sales.forEach(sale => {
            const date = new Date(sale.saleDate).toISOString().split('T')[0];
            allDates.add(date);
        });
        stockTransactions.forEach(transaction => {
            const date = new Date(transaction.transactionDate).toISOString().split('T')[0];
            allDates.add(date);
        });

        // Sort dates in descending order (newest first)
        const sortedDates = Array.from(allDates).sort((a, b) => new Date(b) - new Date(a));

        // Calculate daily income, expenses, and profit
        const dailyData = {};
        sortedDates.forEach(date => {
            dailyData[date] = { income: 0, expenses: 0, profit: 0 };
        });

        // Calculate daily income from sales
        sales.forEach(sale => {
            const date = new Date(sale.saleDate).toISOString().split('T')[0];
            if (dailyData[date]) {
                dailyData[date].income += sale.totalAmount || 0;
            }
        });

        // Calculate daily expenses from stock purchases
        stockTransactions.forEach(transaction => {
            if (transaction.transactionType === 'IN') {
                const date = new Date(transaction.transactionDate).toISOString().split('T')[0];
                const product = products.find(p => p.id === transaction.productId);
                if (dailyData[date] && product) {
                    dailyData[date].expenses += product.purchasePrice * transaction.quantity;
                }
            }
        });

        // Calculate daily profit
        sortedDates.forEach(date => {
            dailyData[date].profit = dailyData[date].income - dailyData[date].expenses;
        });

        // Populate table
        const tableBody = document.getElementById('financialReportTable');
        tableBody.innerHTML = '';

        let totalIncome = 0;
        let totalExpenses = 0;
        let totalProfit = 0;

        sortedDates.forEach(date => {
            const data = dailyData[date];
            totalIncome += data.income;
            totalExpenses += data.expenses;
            totalProfit += data.profit;

            const formattedDate = new Date(date).toLocaleDateString('en-US', { 
                year: 'numeric', 
                month: 'short', 
                day: 'numeric' 
            });

            const profitClass = data.profit >= 0 ? 'text-success' : 'text-danger';

            const row = document.createElement('tr');
            row.innerHTML = `
                <td><strong>${formattedDate}</strong></td>
                <td class="text-end text-primary">₹${data.income.toFixed(2)}</td>
                <td class="text-end text-danger">₹${data.expenses.toFixed(2)}</td>
                <td class="text-end ${profitClass}"><strong>₹${data.profit.toFixed(2)}</strong></td>
            `;
            tableBody.appendChild(row);
        });

        // Update totals
        document.getElementById('totalIncome').textContent = `₹${totalIncome.toFixed(2)}`;
        document.getElementById('totalExpenses').textContent = `₹${totalExpenses.toFixed(2)}`;
        document.getElementById('totalProfit').textContent = `₹${totalProfit.toFixed(2)}`;
        document.getElementById('amountInHand').textContent = `₹${totalProfit.toFixed(2)}`;

        if (sortedDates.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="4" class="text-center text-muted">No financial data available</td></tr>';
        }
    } catch (error) {
        console.error('Error rendering financial report table:', error);
    }
}

function renderFinancialOverview_OLD(sales, stockTransactions, products) {
    const last7Days = [];
    const today = new Date();
    
    for (let i = 6; i >= 0; i--) {
        const date = new Date(today);
        date.setDate(date.getDate() - i);
        last7Days.push(date.toISOString().split('T')[0]);
    }

    // Calculate daily income
    const incomeByDate = {};
    last7Days.forEach(date => incomeByDate[date] = 0);
    sales.forEach(sale => {
        const saleDate = new Date(sale.saleDate).toISOString().split('T')[0];
        if (incomeByDate.hasOwnProperty(saleDate)) {
            incomeByDate[saleDate] += sale.totalAmount;
        }
    });

    // Calculate daily expenses
    const expensesByDate = {};
    last7Days.forEach(date => expensesByDate[date] = 0);
    stockTransactions.forEach(transaction => {
        if (transaction.transactionType === 'IN') {
            const transDate = new Date(transaction.transactionDate).toISOString().split('T')[0];
            if (expensesByDate.hasOwnProperty(transDate)) {
                const product = products.find(p => p.id === transaction.productId);
                if (product) {
                    expensesByDate[transDate] += product.purchasePrice * transaction.quantity;
                }
            }
        }
    });

    // Calculate daily profit
    const profitByDate = {};
    last7Days.forEach(date => {
        profitByDate[date] = incomeByDate[date] - expensesByDate[date];
    });

    const labels = last7Days.map(date => {
        const d = new Date(date);
        return d.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
    });

    const incomeData = last7Days.map(date => incomeByDate[date]);
    const expensesData = last7Days.map(date => expensesByDate[date]);
    const profitData = last7Days.map(date => profitByDate[date]);

    const ctx = document.getElementById('financialOverviewChart');
    if (analyticsCharts.financialOverview) {
        analyticsCharts.financialOverview.destroy();
    }

    analyticsCharts.financialOverview = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Income (₹)',
                    data: incomeData,
                    borderColor: 'rgb(54, 162, 235)',
                    backgroundColor: 'rgba(54, 162, 235, 0.1)',
                    tension: 0.4,
                    fill: true
                },
                {
                    label: 'Expenses (₹)',
                    data: expensesData,
                    borderColor: 'rgb(255, 99, 132)',
                    backgroundColor: 'rgba(255, 99, 132, 0.1)',
                    tension: 0.4,
                    fill: true
                },
                {
                    label: 'Profit (₹)',
                    data: profitData,
                    borderColor: 'rgb(75, 192, 192)',
                    backgroundColor: 'rgba(75, 192, 192, 0.1)',
                    tension: 0.4,
                    fill: true,
                    borderWidth: 3
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            interaction: {
                mode: 'index',
                intersect: false,
            },
            plugins: {
                legend: {
                    display: true,
                    position: 'top'
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return context.dataset.label + ': ₹' + context.parsed.y.toFixed(2);
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

function renderTopProductsChart(products, sales) {
    const productSales = {};
    
    sales.forEach(sale => {
        if (!productSales[sale.productId]) {
            productSales[sale.productId] = 0;
        }
        productSales[sale.productId] += sale.quantity;
    });

    const sortedProducts = Object.entries(productSales)
        .sort((a, b) => b[1] - a[1])
        .slice(0, 5);

    const labels = sortedProducts.map(([id]) => {
        const product = products.find(p => p.id == id);
        return product ? product.name : 'Unknown';
    });

    const data = sortedProducts.map(([, qty]) => qty);

    const ctx = document.getElementById('topProductsChart');
    if (analyticsCharts.topProducts) {
        analyticsCharts.topProducts.destroy();
    }

    analyticsCharts.topProducts = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Units Sold',
                data: data,
                backgroundColor: [
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(255, 206, 86, 0.8)',
                    'rgba(75, 192, 192, 0.8)',
                    'rgba(153, 102, 255, 0.8)'
                ],
                borderWidth: 0
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { display: false }
            },
            scales: {
                y: { beginAtZero: true }
            }
        }
    });
}

function renderSalesTrendChart(sales) {
    const last7Days = [];
    const today = new Date();
    
    for (let i = 6; i >= 0; i--) {
        const date = new Date(today);
        date.setDate(date.getDate() - i);
        last7Days.push(date.toISOString().split('T')[0]);
    }

    const salesByDate = {};
    last7Days.forEach(date => salesByDate[date] = 0);

    sales.forEach(sale => {
        const saleDate = new Date(sale.saleDate).toISOString().split('T')[0];
        if (salesByDate.hasOwnProperty(saleDate)) {
            salesByDate[saleDate] += sale.totalAmount;
        }
    });

    const labels = last7Days.map(date => {
        const d = new Date(date);
        return d.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
    });

    const data = last7Days.map(date => salesByDate[date]);

    const ctx = document.getElementById('salesTrendChart');
    if (analyticsCharts.salesTrend) {
        analyticsCharts.salesTrend.destroy();
    }

    analyticsCharts.salesTrend = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Revenue (₹)',
                data: data,
                borderColor: 'rgb(75, 192, 192)',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                tension: 0.4,
                fill: true
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { display: true }
            },
            scales: {
                y: { beginAtZero: true }
            }
        }
    });
}

function renderProfitMarginChart(products, sales) {
    const productProfits = {};

    sales.forEach(sale => {
        const product = products.find(p => p.id === sale.productId);
        if (product) {
            if (!productProfits[product.id]) {
                productProfits[product.id] = {
                    name: product.name,
                    margin: ((product.sellingPrice - product.purchasePrice) / product.sellingPrice * 100)
                };
            }
        }
    });

    const sortedProducts = Object.values(productProfits)
        .sort((a, b) => b.margin - a.margin)
        .slice(0, 5);

    const labels = sortedProducts.map(p => p.name);
    const data = sortedProducts.map(p => p.margin.toFixed(2));

    const ctx = document.getElementById('profitMarginChart');
    if (analyticsCharts.profitMargin) {
        analyticsCharts.profitMargin.destroy();
    }

    analyticsCharts.profitMargin = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                data: data,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 206, 86, 0.8)',
                    'rgba(75, 192, 192, 0.8)',
                    'rgba(153, 102, 255, 0.8)'
                ]
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { position: 'right' },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return context.label + ': ' + context.parsed + '%';
                        }
                    }
                }
            }
        }
    });
}

function renderRevenueProfitChart(sales, products) {
    let totalRevenue = 0;
    let totalProfit = 0;

    sales.forEach(sale => {
        const product = products.find(p => p.id === sale.productId);
        if (product) {
            totalRevenue += sale.totalAmount;
            totalProfit += (product.sellingPrice - product.purchasePrice) * sale.quantity;
        }
    });

    const totalCost = totalRevenue - totalProfit;

    const ctx = document.getElementById('revenueProfitChart');
    if (analyticsCharts.revenueProfit) {
        analyticsCharts.revenueProfit.destroy();
    }

    analyticsCharts.revenueProfit = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Financial Overview'],
            datasets: [
                {
                    label: 'Revenue',
                    data: [totalRevenue],
                    backgroundColor: 'rgba(54, 162, 235, 0.8)'
                },
                {
                    label: 'Cost',
                    data: [totalCost],
                    backgroundColor: 'rgba(255, 99, 132, 0.8)'
                },
                {
                    label: 'Profit',
                    data: [totalProfit],
                    backgroundColor: 'rgba(75, 192, 192, 0.8)'
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { display: true }
            },
            scales: {
                y: { beginAtZero: true }
            }
        }
    });
}

function renderPerformanceTable(products, sales, stock) {
    const tableBody = document.getElementById('performanceTable');
    tableBody.innerHTML = '';

    const productMetrics = {};

    sales.forEach(sale => {
        if (!productMetrics[sale.productId]) {
            productMetrics[sale.productId] = {
                unitsSold: 0,
                revenue: 0,
                profit: 0
            };
        }
        const product = products.find(p => p.id === sale.productId);
        if (product) {
            productMetrics[sale.productId].unitsSold += sale.quantity;
            productMetrics[sale.productId].revenue += sale.totalAmount;
            productMetrics[sale.productId].profit += (product.sellingPrice - product.purchasePrice) * sale.quantity;
        }
    });

    const sortedMetrics = Object.entries(productMetrics)
        .sort((a, b) => b[1].revenue - a[1].revenue)
        .slice(0, 10);

    sortedMetrics.forEach(([productId, metrics]) => {
        const product = products.find(p => p.id == productId);
        if (!product) return;

        const stockItem = stock.find(s => s.productId == productId);
        const currentStock = stockItem ? stockItem.quantity : 0;
        const profitMargin = (metrics.profit / metrics.revenue * 100).toFixed(1);
        const minStockThreshold = product.minStock || 10;

        let stockStatus = '';
        if (currentStock === 0) {
            stockStatus = '<span class="badge bg-danger">Out of Stock</span>';
        } else if (currentStock < minStockThreshold) {
            stockStatus = '<span class="badge bg-warning">Low Stock</span>';
        } else {
            stockStatus = '<span class="badge bg-success">In Stock</span>';
        }

        const row = document.createElement('tr');
        row.innerHTML = `
            <td><strong>${product.name}</strong></td>
            <td>${metrics.unitsSold}</td>
            <td>₹${metrics.revenue.toFixed(2)}</td>
            <td class="text-success">₹${metrics.profit.toFixed(2)}</td>
            <td><span class="badge bg-info">${profitMargin}%</span></td>
            <td>${stockStatus}</td>
        `;
        tableBody.appendChild(row);
    });

    if (sortedMetrics.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="6" class="text-center text-muted">No sales data available</td></tr>';
    }
}

// Call this function when dashboard loads
if (typeof window !== 'undefined') {
    window.loadEnhancedAnalytics = loadEnhancedAnalytics;
}
