## About Spring AI / Langchain4j
1. LLM API를 찌르는 방식으로 구성
 - huggingface도 마찬가지...
2. Ollama
 - Dockerize된 LLM 서빙 툴
 - run
    - docker run -d -v $PWD/data/models:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
    - docker exec -it ollama ollama run gemma:2b
3. local AI
 - 로컬에서 AI 테스트를 위한 서빙 툴
 - run
    - docker run -ti -p 8080:8080 --name local-ai -v $PWD/data/models:/build/model  localai/localai:v2.16.0-ffmpeg-core

## TODO
- Spring AI SQL Agent with 'Function Calling'
- Langchain4j SQL Agent with '@Tool'
- Custom Chat Client Implementation


### How to create reAct Agent?
https://github.com/QwenLM/Qwen/blob/main/examples/react_prompt.md
