# 📈 FinSight – Portfolio Manager

FinSight is a Java-based desktop application for managing and analyzing your stock portfolio with a special focus on **value** and **dividend growth investing**. It tracks your holdings, watchlist, and dividend income, providing detailed insights into your portfolio’s performance over time.

---

## ✨ Features

### 🔍 Stock Tracking
- Supports **U.S. stocks, ETFs, and mutual funds**
- Track real-time and historical prices using **Yahoo Finance**
- Watchlist (`Watch`), wishlist (`Goal`), and avoid list (`Bench`) for categorizing stocks

### 💼 Portfolio Management
- Add open or closed **BUY/SELL/DIVIDEND** transactions
- Automatically update real-time prices (including pre/after-market)
- View interactive price graphs (10Y, 1Y, 10D, intraday)

### 📊 Stock-Level Analytics
- Ticker symbol, name, last price, % change
- Valuation: current P/E, target price, target index
- Income: annual dividend, yield, 5Y DGR, dividend streak
- Morningstar rating and user-defined credit rating
- Personal notes support

### 💸 Position-Level Metrics
- Number of shares, cost basis, market value
- Unrealized gain/loss, yield on cost (YoC)
- Realized and received income
- Total return breakdown

### 📈 Portfolio Statistics
- Aggregated view: market value, unrealized result, income, return
- Time-based breakdown: monthly, quarterly, yearly, and overall
- Optional dividend tax handling
- Export CAGR and price discount analytics to CSV

---

## 💾 Data & Storage

- All data stored in a single `JSON` file (`data/portfolio.json`)
- Application logging enabled (`log/portfolio-manager.log`)

---

## 🛠️ Tech Stack

- **Java SE (JDK 8)** – Application logic and GUI
- **Swing** – Desktop interface
- **Maven** – Build and dependency management
- **Yahoo Finance API** – Market data integration

---

## 🚀 Installation

1. Install Java 8 or higher.
2. Download the standalone executable JAR file.
3. Run it via terminal or double-click:
   ```bash
   java -jar portfolio-manager.jar
📂 Project Structure
bash
Copy
Edit
portfolio-manager/
├── data/                     # Portfolio JSON storage
├── log/                      # Application logs
├── src/                      # Java source files
├── pom.xml                   # Maven configuration
└── portfolio-manager.jar     # Executable output
📚 License
This project is licensed under the Apache 2.0 License.
You are free to use, modify, and distribute it with attribution.
See LICENSE.txt for details.

🔗 Source Code
GitHub: https://github.com/swirleyythrone/FinSight/

yaml
Copy
Edit

---










