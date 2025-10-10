#!/bin/bash

##############################################################################
# Script to build and run the EDP Serial Number Service
##############################################################################

set -e

echo "========================================="
echo "EDP Serial Number Service - Startup"
echo "========================================="
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed or not in PATH"
    echo "Please install Java 17 or higher"
    exit 1
fi

# Display Java version
echo "Java version:"
java -version
echo ""

# Navigate to project directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# Build the project
echo "Building the project..."
./gradlew clean build -x test
echo "✅ Build completed successfully"
echo ""

# Run the application
echo "Starting EDP Serial Number Service..."
echo "========================================="
java -jar build/libs/edp-serialno-service-1.0.0.jar
