<p align="center">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/🚚-Delivery%20App-orange?style=for-the-badge" alt="Delivery">
</p>

<h1 align="center">🚚 Delivery App</h1>
<h3 align="center">Android App for Delivery Personnel – Order Management</h3>

<p align="center">
  <strong>A Kotlin Android app for delivery personnel to manage orders. View, confirm, deliver, and track orders through different statuses: Waiting, Confirmed, Paid, In Delivery, and Cancelled.</strong>
</p>

<p align="center">
  <a href="#-features">Features</a> •
  <a href="#-order-statuses">Order Statuses</a> •
  <a href="#-getting-started">Getting Started</a> •
  <a href="#-project-structure">Project Structure</a> •
  <a href="#-configuration">Configuration</a>
</p>

---

## 📖 Overview

This Android app is designed for delivery personnel (delivery men) to manage orders. It uses tabs/fragments to organize orders by status: Waiting, Confirmed, Paid, In Delivery (Livery), and Cancelled. Includes Firebase (Google Services) for backend integration.

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🔐 **Login** | Delivery personnel authentication |
| 🏠 **Home** | Main dashboard with order overview |
| ⏳ **Waiting Orders** | Orders awaiting confirmation |
| ✅ **Confirmed Orders** | Orders confirmed for delivery |
| 💰 **Paid Orders** | Paid orders ready for pickup |
| 🚚 **Delivery (Livery)** | Orders in delivery |
| ❌ **Cancelled Orders** | Cancelled orders |
| 📋 **Order Details** | View full order information |
| 👤 **Delivery Info** | Delivery personnel profile/info |
| 🔔 **Real-time Updates** | Listeners for order changes |

---

## 📊 Order Statuses

| Status | Fragment | Description |
|--------|----------|-------------|
| **Waiting** | `FragmentWaiting` | New orders waiting for confirmation |
| **Confirmed** | `FragmentConfirmed` | Orders confirmed by delivery person |
| **Paid** | `FragmentPaid` | Orders paid, ready for delivery |
| **In Delivery** | `FragmentLivery` | Orders currently being delivered |
| **Cancelled** | `FragmentCancel` | Cancelled orders |

---

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin |
| **Platform** | Android |
| **Backend** | Firebase (Google Services) |
| **Architecture** | Activities, Fragments, RecyclerViews |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio (Arctic Fox or newer)
- JDK 8 or higher
- Android SDK (minSdk 21+)
- Firebase project with `google-services.json`

### Installation

```bash
# Clone the repository
git clone https://github.com/ezriouil/delivery.git
cd delivery

# Open in Android Studio
# File → Open → Select project folder

# Sync Gradle
# Build → Sync Project with Gradle Files
```

### Firebase Setup

1. Create a project at [Firebase Console](https://console.firebase.google.com)
2. Add an Android app with package name: `www.ezriouil.delivery`
3. Download `google-services.json` and place it in the `app/` folder
4. Enable required Firebase services (Auth, Firestore/Realtime DB, etc.)

### Run the App

```bash
# Using Gradle
./gradlew installDebug

# Or run from Android Studio
# Run → Run 'app'
```

---

## 📁 Project Structure

```
delivery/
├── .idea/                          # IDE settings
├── admin/                          # Admin module (if applicable)
├── app/
│   ├── src/
│   │   ├── androidTest/            # Instrumented tests
│   │   │   └── java/www/ezriouil/delivery/
│   │   ├── debug/                  # Debug build config
│   │   ├── main/
│   │   │   ├── java/www/ezriouil/delivery/
│   │   │   │   ├── annuler/cancel/ # Cancelled orders
│   │   │   │   │   ├── CancelOrdersRV.kt
│   │   │   │   │   └── FragmentCancel.kt
│   │   │   │   ├── confirmer/      # Confirmed orders
│   │   │   │   │   ├── ConfirmOrdersRV.kt
│   │   │   │   │   └── FragmentConfirmed.kt
│   │   │   │   ├── livrey/         # In delivery
│   │   │   │   │   ├── FragmentLivery.kt
│   │   │   │   │   └── LiveryOrdersRV.kt
│   │   │   │   ├── paid/           # Paid orders
│   │   │   │   │   ├── FragmentPaid.kt
│   │   │   │   │   └── PaidOrdersRV.kt
│   │   │   │   ├── waiting/        # Waiting orders
│   │   │   │   │   ├── FragmentWaiting.kt
│   │   │   │   │   └── WaitingOrdersRV.kt
│   │   │   │   ├── AllListener.kt
│   │   │   │   ├── Constants.kt
│   │   │   │   ├── DeliveryMenInfo.kt
│   │   │   │   ├── Home.kt
│   │   │   │   ├── Login.kt
│   │   │   │   └── OrderInfo.kt
│   │   │   └── res/                # Resources
│   │   └── test/                   # Unit tests
│   ├── AndroidManifest.xml
│   ├── build.gradle
│   ├── google-services.json
│   └── proguard-rules.pro
├── gradle/
│   ├── wrapper/
│   ├── .gitignore
│   ├── build.gradle
│   ├── gradle.properties
│   ├── gradlew
│   ├── gradlew.bat
│   └── settings.gradle
├── .gitignore
└── README.md
```

---

## 📱 App Flow

```
┌─────────┐     ┌─────────┐     ┌──────────────────────────────┐
│  Login  │────▶│  Home   │────▶│  Order Tabs (ViewPager?)     │
└─────────┘     └─────────┘     │  • Waiting                   │
                                │  • Confirmed                 │
                                │  • Paid                      │
                                │  • In Delivery (Livery)      │
                                │  • Cancelled                 │
                                └─────────────┬────────────────┘
                                              │
                                              ▼
                                        ┌──────────────┐
                                        │  Order Info  │
                                        └──────────────┘
```

---

## 📦 Key Components

| File | Description |
|------|-------------|
| `Login.kt` | Login screen for delivery personnel |
| `Home.kt` | Main screen with order tabs |
| `OrderInfo.kt` | Order details screen |
| `DeliveryMenInfo.kt` | Delivery personnel info |
| `Constants.kt` | App constants and config |
| `AllListener.kt` | Firebase/data listeners |
| `*OrdersRV.kt` | RecyclerView adapters for each order list |
| `Fragment*.kt` | Fragments for each order status tab |

---

## 🔒 Package

**Application ID:** `www.ezriouil.delivery`

---

## 📄 License

MIT License

---

## 👤 Author

**Mohamed Ezriouil**
- GitHub: [@ezriouil](https://github.com/ezriouil)

---

<p align="center">⭐ Star this repo if you find it helpful!</p>
