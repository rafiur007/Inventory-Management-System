# Simple Inventory Management System 

This is our university project for Introduction to Programming Language II (JAVA). It's a desktop application built using Java Swing to track and manage inventory items.

## What it does
It's a simple GUI app that lets you add and remove items from a list. It uses a table to display the data and has validation to stop you from entering negative quantities, empty fields, or duplicate IDs.

## Features
- Add "Standard" and "Premium" items.
- Delete selected items from the table.
- Input validation (checks for empty fields, valid numbers, and duplicate IDs).
- Custom Exception handling for cleaner error messages.

## Tech Stack
- **Language:** Java
- **GUI:** Java Swing (AWT)
- **IDE:** IntelliJ IDEA

## Project Structure
We split the code into different packages to keep it clean and follow OOP principles:
- `gui`: Contains the JFrame, Swing components, and UI logic.
- `model`: The backend classes (`Item`, `StandardItem`, `PremiumItem`).
- `manager`: Handles the business logic and data storage (ArrayList).
- `exception`: Custom exception handling.

## How to run it
1. Clone this repo.
2. Open the project in IntelliJ IDEA.
3. Navigate to `src > gui > InventoryManagementApp.java`.
4. Run the `main` method.

---
*Built by*
- M.Rafi-Ur-Rahman Alif (ID:2024100000006)
- Rafa Rashid (ID:2024100000036)
- Amy Chicham (ID:2024100000027)
- Ramisa Maliat Zarin (ID:2024100000035)
- Alif Khatun Bristi (ID:2024100000004)
