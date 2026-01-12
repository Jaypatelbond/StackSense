# Contributing to StackSense

Thank you for your interest in contributing to StackSense! Let's make this project better.

## 🤝 How to Contribute

### 1. Fork the Repository
Click the "Fork" button at the top right of the repository page to create your own copy of the project.

### 2. Clone Your Fork
Clone your forked repository to your local machine:
```bash
git clone https://github.com/jaypatelbond/StackSense.git
cd StackSense
```

### 3. Create a Branch
Create a new branch for your feature or bug fix:
```bash
git checkout -b feature/amazing-feature
# or
git checkout -b fix/critical-bug
```

### 4. Make Changes
Make your changes to the code. Ensure you follow the project's coding standards and style.
- Use **Kotlin** for all Android code.
- Follow **Jetpack Compose** best practices.
- Ensure the app works **offline**.

### 5. Test Your Changes
Build and run the app to verify your changes:
```bash
./gradlew assembleDebug
```
Make sure everything works as expected and you haven't introduced any regressions.

### 6. Commit Your Changes
Commit your changes with a clear and descriptive message:
```bash
git commit -m "Add amazing feature to analyze compiled code"
```

### 7. Push to Your Fork
Push your changes to your forked repository:
```bash
git push origin feature/amazing-feature
```

### 8. Open a Pull Request
Go to the original StackSense repository on GitHub and open a Pull Request (PR) from your fork. Provide a clear description of your changes and why they are needed.

## 📝 Code Style
- Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html).
- Use `PascalCase` for composables and classes.
- Use `camelCase` for functions and variables.

## 🐞 Reporting Bugs
If you find a bug, please open an issue on GitHub with the following details:
- Steps to reproduce the bug.
- Build version of the app.
- Android version and device model.
- Expected behavior vs. actual behavior.

## 💡 Feature Requests
Let's add new ideas! If you have a feature request, please open an issue describing the feature and how it would benefit the project.

Thank you for contributing! 🚀
