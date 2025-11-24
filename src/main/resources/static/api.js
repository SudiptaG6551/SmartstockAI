// Central API service with JWT and Role management
class ApiService {
    constructor() {
        // Use current host and port, works from any device
        this.baseUrl = window.location.origin;
    }

    getToken() {
        return localStorage.getItem('jwtToken');
    }

    getUserRole() {
        return localStorage.getItem('userRole');
    }

    getUserId() {
        return localStorage.getItem('userId');
    }

    isAuthenticated() {
        return !!this.getToken();
    }

    // Decode JWT token to extract role and user_id
    decodeToken(token) {
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            return {
                userId: payload.user_id,
                role: payload.role,
                exp: payload.exp
            };
        } catch (error) {
            console.error('Failed to decode token:', error);
            return null;
        }
    }

    storeUserData(token) {
        localStorage.setItem('jwtToken', token);
        const decoded = this.decodeToken(token);
        if (decoded) {
            localStorage.setItem('userRole', decoded.role);
            localStorage.setItem('userId', decoded.userId);
            return decoded.role;
        }
        return null;
    }

    logout() {
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('userRole');
        localStorage.removeItem('userId');
        window.location.href = '/login.html';
    }

    async authenticatedFetch(url, options = {}) {
        const token = this.getToken();
        
        if (!token) {
            this.redirectToLogin();
            throw new Error('Not authenticated');
        }

        const headers = {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
            ...options.headers
        };

        try {
            const response = await fetch(url, { ...options, headers });
            
            if (response.status === 401) {
                this.logout();
                throw new Error('Session expired');
            }

            if (response.status === 403) {
                throw new Error('Access denied - insufficient permissions');
            }

            return response;
        } catch (error) {
            console.error('API call failed:', error);
            throw error;
        }
    }

    redirectToLogin() {
        if (!window.location.href.includes('login.html')) {
            window.location.href = '/login.html';
        }
    }

    redirectToDashboard() {
        const role = this.getUserRole();
        const dashboardMap = {
            'OWNER': '/dashboard/owner_dashboard.html',
            'SALES': '/dashboard/sales_dashboard.html', 
            'STOCK_MANAGER': '/dashboard/stock_dashboard.html'
        };
        
        const dashboardUrl = dashboardMap[role];
        if (dashboardUrl && !window.location.href.includes(dashboardUrl)) {
            window.location.href = dashboardUrl;
        }
    }

    // Check if user has required role
    hasRole(requiredRole) {
        const userRole = this.getUserRole();
        return userRole === requiredRole;
    }

    // Product APIs
    async getProducts() {
        const response = await this.authenticatedFetch(`${this.baseUrl}/api/products`);
        return await response.json();
    }

    async createProduct(productData) {
        const response = await this.authenticatedFetch(`${this.baseUrl}/api/products`, {
            method: 'POST',
            body: JSON.stringify(productData)
        });
        
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Failed to create product: ${response.status} - ${errorText}`);
        }
        
        return await response.json();
    }

    async updateProduct(productId, productData) {
        const response = await this.authenticatedFetch(`${this.baseUrl}/api/products/${productId}`, {
            method: 'PUT',
            body: JSON.stringify(productData)
        });
        return await response.json();
    }

    async deleteProduct(productId) {
        const response = await this.authenticatedFetch(`${this.baseUrl}/api/products/${productId}`, {
            method: 'DELETE'
        });
        return response;
    }

    // Stock APIs
    async getStock() {
        const response = await this.authenticatedFetch(`${this.baseUrl}/api/stock`);
        return await response.json();
    }

    async receiveStock(stockData) {
        const response = await this.authenticatedFetch(`${this.baseUrl}/api/stock/receive`, {
            method: 'POST',
            body: JSON.stringify(stockData)
        });
        return response.ok;
    }

    async getLowStock() {
        const response = await this.authenticatedFetch(`${this.baseUrl}/api/stock/low-stock`);
        return await response.json();
    }

    async getStockTransactions() {
        const response = await this.authenticatedFetch(`${this.baseUrl}/api/stock/transactions`);
        return await response.json();
    }

    // Sales APIs
    async recordSale(saleData) {
        const userId = this.getUserId();
        const saleWithUser = { ...saleData, userId: parseInt(userId) };
        const response = await this.authenticatedFetch(`${this.baseUrl}/api/sales`, {
            method: 'POST',
            body: JSON.stringify(saleWithUser)
        });
        return await response.json();
    }

    async getSales() {
        const response = await this.authenticatedFetch(`${this.baseUrl}/api/sales`);
        return await response.json();
    }

    // Dashboard APIs
    async getDashboardSummary() {
        const response = await this.authenticatedFetch(`${this.baseUrl}/api/owner/summary`);
        return await response.json();
    }

    async getProfitExpenseReport(startDate, endDate) {
        const response = await this.authenticatedFetch(
            `${this.baseUrl}/api/profit-expense/report?startDate=${startDate}&endDate=${endDate}`
        );
        return await response.json();
    }

    // ML Forecast APIs
    async getForecast(productId) {
        const response = await this.authenticatedFetch(`${this.baseUrl}/api/predictions/forecast/${productId}`);
        return await response.json();
    }

    // Owner APIs
    async resetDatabase() {
        const response = await this.authenticatedFetch(`${this.baseUrl}/api/owner/reset-database`, {
            method: 'POST'
        });
        return await response.json();
    }
}

// Global API instance
const apiService = new ApiService();