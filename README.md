# Telegram Logging Bot
A bot that reads logs from a file and sends them to Telegram.
### Requirements

- **Java**: 17 or higher
- **PostgreSQL**: for storing data
- **Telegram Bot API Token**: required for the bot to work (can be obtained from [BotFather](https://t.me/BotFather))
- **Environment variables**:
- `TELEGRAM_DATASOURCE_URL`: Database connection URL. Parameter `--spring.datasource.url` Example `--spring.datasource.url=jdbc:postgresql://localhost:5432/{database_name}`
- `TELEGRAM_DATASOURCE_USERNAME`: Database username. Parameter `--spring.datasource.username`
- `TELEGRAM_DATASOURCE_PASSWORD`: Database password. Parameter `--spring.datasource.password`
- `TELEGRAM_LOGGING_TOKEN`: Telegram bot token. Parameter `--telegram.token`
- `TELEGRAM_LOGGING_FILE` Log file name (default: `log.log`). Parameter `--telegram.file
### Launch
In the console, enter the command in the same directory as the bot.

```
java -jar telegram-logging-bot {params}
```

The bot runs on port 8085. To change the port, use the parameter `--server.port`. For example:

```
java -jar telegram-logging-bot --server.port=8080
```

### Usage

- **Registration**:
	- Write the bot the command `/start`.
	- Get the confirmation code in the application console.
	- Enter the code in the chat with the bot.
- **Receiving logs**:
	- The bot will start sending you logs from the file specified in `--telegram.file` or `TELEGRAM_LOGGING_FILE`.
