$headers = @{
    "Content-Type" = "application/json"
}

Write-Host "=== Testing All User Logins ===" -ForegroundColor Cyan
Write-Host ""

# Test Owner
Write-Host "1. Testing OWNER (sudipta)..." -ForegroundColor Yellow
$body = @{ username = "sudipta"; password = "password" } | ConvertTo-Json
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/auth/login" -Method POST -Headers $headers -Body $body -UseBasicParsing
    Write-Host "   SUCCESS! Status: $($response.StatusCode)" -ForegroundColor Green
    $content = $response.Content | ConvertFrom-Json
    Write-Host "   Token received: $($content.token.Substring(0,20))..." -ForegroundColor Green
} catch {
    Write-Host "   FAILED! $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""

# Test Stock Manager
Write-Host "2. Testing STOCK_MANAGER (prabir)..." -ForegroundColor Yellow
$body = @{ username = "prabir"; password = "password" } | ConvertTo-Json
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/auth/login" -Method POST -Headers $headers -Body $body -UseBasicParsing
    Write-Host "   SUCCESS! Status: $($response.StatusCode)" -ForegroundColor Green
    $content = $response.Content | ConvertFrom-Json
    Write-Host "   Token received: $($content.token.Substring(0,20))..." -ForegroundColor Green
} catch {
    Write-Host "   FAILED! $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""

# Test Sales
Write-Host "3. Testing SALES (mousumi)..." -ForegroundColor Yellow
$body = @{ username = "mousumi"; password = "password" } | ConvertTo-Json
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/auth/login" -Method POST -Headers $headers -Body $body -UseBasicParsing
    Write-Host "   SUCCESS! Status: $($response.StatusCode)" -ForegroundColor Green
    $content = $response.Content | ConvertFrom-Json
    Write-Host "   Token received: $($content.token.Substring(0,20))..." -ForegroundColor Green
} catch {
    Write-Host "   FAILED! $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== Test Complete ===" -ForegroundColor Cyan
