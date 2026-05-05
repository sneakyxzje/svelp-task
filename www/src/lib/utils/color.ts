export const getPriorityColor = (priority: string) => {
	switch (priority) {
		case 'Urgent':
			return 'text-red-400 bg-red-400/10';
		case 'High':
			return 'text-orange-400 bg-orange-400/10';
		case 'Medium':
			return 'text-blue-400 bg-blue-400/10';
		default:
			return 'text-text-tertiary bg-white/5';
	}
};
