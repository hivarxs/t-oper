# tOper - Minecraft Server Optimization Plugin

## Overview
tOper is a comprehensive optimization plugin for Paper 1.8 servers, designed to improve performance through various optimization techniques.

## Core Features

### 1. Inventory Optimization
- Restrict unnecessary item movements
- Limit hopper operations per chunk
- Control crafting and smelting operations

### 2. Entity Management
- Dynamic entity limiting per chunk
- Configurable entity type exclusions
- Automatic cleanup of excess entities

### 3. Redstone Optimization
- Redstone update throttling
- Clock circuit detection and limitation
- Per-chunk update limiting

### 4. Anti-Lag Systems
- Automatic item cleanup
- Hopper transfer rate limiting
- Mob AI optimization
- Memory usage monitoring

### 5. Performance Monitoring
- TPS tracking and alerts
- Memory usage monitoring
- Admin notification system

## Technical Implementation

### Main Components
1. TOper.java - Main plugin class
2. Managers
   - ConfigManager - Configuration handling
   - PerformanceManager - TPS and memory monitoring
   - EntityManager - Entity limiting and cleanup
   - RedstoneManager - Redstone optimization
   - InventoryManager - Inventory and hopper management

### Event Listeners
1. EntityListener - Entity spawn control
2. InventoryListener - Inventory operation optimization
3. RedstoneListener - Redstone update management

### Commands
- /toper reload - Reload configuration
- /toper status - Show performance statistics

## Configuration
- Configurable thresholds for all features
- Per-chunk entity limits
- Redstone update limits
- Performance alert thresholds

## Development Timeline
1. ✓ Core plugin structure
2. ✓ Configuration system
3. ✓ Entity management
4. ✓ Redstone optimization
5. ✓ Inventory control
6. ✓ Performance monitoring
7. ✓ Admin commands
8. Testing and optimization