typedef struct {
	char cmd;
	int hasArg;
	void (*func)(int, int);
} Command;

static Command cmds[] = {
	{ 'P', ARG, doSelectPen },
	{ 'U', NO_ARG, doPenUp },
	{ 'D', NO_ARG, doPenDown },
	{ 'N', ARG, doPenDir },
	{ 'E', ARG, doPenDir },
	{ 'S', ARG, doPenDir },
	{ 'W', ARG, doPenDir }
};

while (fgets(buff, sizeof(buff), stdin)) {
	Command *cmd = findCommand(*buff);
	if (cmd) {
		int arg = 0;
		if (cmd -> hasArg && !getArg(buff + 1, &arg)) {
			fprintf(stderr, "'%c' needs an argument\n", *buff);
			continue;
		}
		cmd -> func(*buff, arg);
	}
}

Command *findCommand(int cmd) {
	int i;
	for (i = 0; i < ARRAY_SIZE(cmds); i++) {
		if (cmds[i].cmd == cmd) {
			return cmds + i;
		}
	}
	fprintf(stderr, "Unknown command '%c'\n", cmd);
	return 0;	
}

int getArg(const char *buff, int *result) {
	return sscanf(buff, "%d", result) == 1;
}
