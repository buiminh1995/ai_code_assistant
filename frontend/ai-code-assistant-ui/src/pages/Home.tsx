import {
  Box,
  Button,
  TextField,
  Typography,
} from "@mui/material";

import { useRef, useState } from "react";

import { importRepository, askRepository } from "../api/repositoryApi";

type ChatMessage = {
  id: number;
  role: "user" | "assistant";
  content: string;
  isLoading?: boolean;
};

export default function Home() {
  const [repositoryUrl, setRepositoryUrl] = useState("https://github.com/spring-projects/spring-petclinic");
  const [question, setQuestion] = useState("");
  const [repositoryId, setRepositoryId] = useState<number | null>(1);
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const nextMessageId = useRef(1);

  const handleImport = async () => {
    const repo = await importRepository(repositoryUrl);
    console.log(repo.id)
    setRepositoryId(repo.id);
  }

  const handleAsk = async () => {
    if (!repositoryId || !question.trim()) return;

    const userQuestion = question;
    const userMessageId = nextMessageId.current++;
    const assistantMessageId = nextMessageId.current++;
    setQuestion("");

    setMessages((prev) => [
      ...prev,
      { id: userMessageId, role: "user", content: userQuestion },
      { id: assistantMessageId, role: "assistant", content: "", isLoading: true },
    ]);

    try {
      const result = await askRepository(repositoryId, userQuestion);

      setMessages((prev) =>
        prev.map((message) =>
          message.id === assistantMessageId
            ? { ...message, content: result || "No answer returned.", isLoading: false }
            : message,
        ),
      );
    } catch {
      setMessages((prev) =>
        prev.map((message) =>
          message.id === assistantMessageId
            ? { ...message, content: "Sorry, I couldn't retrieve an answer.", isLoading: false }
            : message,
        ),
      );
    }
  };

  return (
    <Box className="chat-shell">
      <Box className="sidebar">
        <div className="title-container">
          <Typography>AI Code Assistant</Typography>
        </div>

        <TextField
          label="Repository URL"
          value={repositoryUrl}
          onChange={(e) => setRepositoryUrl(e.target.value)}
        />

        <Button onClick={handleImport}>Import</Button>
      </Box>

      <Box className="chat-main">
        <Box className="messages">
          {messages.map((message) => (
            <Box key={message.id} className={`message ${message.role}`}>
              {message.isLoading ? (
                <div className="loader-container" aria-label="Assistant is typing">
                  <div className="dot"></div>
                  <div className="dot"></div>
                  <div className="dot"></div>
                </div>
              ) : (
                <Typography>{message.content}</Typography>
              )}
            </Box>
          ))}
        </Box>

        <Box className="composer">
          <TextField
            placeholder="Ask anything about this repository..."
            value={question}
            onChange={(e) => setQuestion(e.target.value)}
            fullWidth
          />
          <Button onClick={handleAsk}>Send</Button>
        </Box>
      </Box>
    </Box>
    // <Container maxWidth="md">
    //   <Stack spacing={4} sx={{ mt: 6 }}>
    //     <Typography variant="h3" align="center">
    //       AI Code Assistant
    //     </Typography>

    //     <Card>
    //         <CardContent>
    //             <Stack spacing={2}>
    //                 <Typography variant="h6">
    //                     Import Repository
    //                 </Typography>

    //                 <TextField
    //                 label="GitHub Repository URL"
    //                 fullWidth
    //                 value={repositoryUrl}
    //                 onChange={(e) =>
    //                     setRepositoryUrl(e.target.value)
    //                 }
    //                 />

    //                 <Button
    //                     variant="contained"
    //                     onClick={async () => {
    //                         const repo = await importRepository(repositoryUrl);
    //                         console.log(repo.id)
    //                         setRepositoryId(repo.id);
    //                     }}
    //                     >
    //                     Import Repository
    //                 </Button>
    //             </Stack>
    //         </CardContent>
    //     </Card>

    //     <Card>
    //         <CardContent>
    //         <Stack spacing={2}>
    //           <Typography variant="h6">
    //             Ask a Question
    //           </Typography>

    //           <TextField
    //             label="Question"
    //             fullWidth
    //             value={question}
    //             onChange={(e) =>
    //               setQuestion(e.target.value)
    //             }
    //           />

    //         <Button
    //             variant="contained"
    //             onClick={async () => {
    //             if (!repositoryId) return;
    //             const result = await askRepository(repositoryId, question);
    //             setAnswer(result);
    //             }}
    //         >
    //         Ask
    //         </Button>

    //           <Box>
    //             <Typography variant="subtitle1">
    //               Answer
    //             </Typography>

    //             <Typography>
    //               {answer || "No answer yet."}
    //             </Typography>
    //           </Box>
    //         </Stack>
    //       </CardContent>
    //     </Card>
    //   </Stack>
    // </Container>
  );
}
