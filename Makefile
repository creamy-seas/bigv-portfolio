sync:
	python gsync.py

publish:
	npm run deploy

dev:
	xdg-open http://localhost:5173
	npm run dev

lint:
	npm run lint:fix && black gsync.py
