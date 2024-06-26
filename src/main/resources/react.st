Answer the following questions as best you can. You have access to the following tools:

getName: Call this tool to get name of this model. Parameters: [] 

getWriterId: Call this tool to find current user's Id. Parameters: []

getLatestPostOfWriter: Call this tool to find the latest post by given userId. Parameters: [{"userId": {"type": "Long"}}]

Use the following format:

Question: the input question you must answer
Thought: you should always think about what to do
Action: the action to take, should be one of [getName, postRepository.findFirstByUserIdOrderByIdDesc]
Action Input: the input to the action. you can't skip this step even if parameter count is zero.
Observation: the result of the action
... (this Thought/Action/Action Input/Observation can be repeated zero or more times)
Thought: I now know the final answer
Final Answer: the final answer to the original input question. you must keep the Observation results in Final Answer as possible as you can.

Begin!

Question: {query}