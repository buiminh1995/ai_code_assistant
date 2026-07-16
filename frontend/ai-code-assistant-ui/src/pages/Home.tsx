import {
  Box,
  Button,
  Card,
  CardContent,
  Container,
  Stack,
  TextField,
  Typography,
} from "@mui/material";

import { useState } from "react";

import { importRepository, askRepository } from "../api/repositoryApi";

export default function Home() {
  const [repositoryUrl, setRepositoryUrl] = useState("https://github.com/spring-projects/spring-petclinic");
  const [question, setQuestion] = useState("");
  const [answer, setAnswer] = useState("");
  const [repositoryId, setRepositoryId] = useState<number | null>(1);

  return (
    <Container maxWidth="md">
      <Stack spacing={4} sx={{ mt: 6 }}>
        <Typography variant="h3" align="center">
          AI Code Assistant
        </Typography>

        <Card>
            <CardContent>
                <Stack spacing={2}>
                    <Typography variant="h6">
                        Import Repository
                    </Typography>

                    <TextField
                    label="GitHub Repository URL"
                    fullWidth
                    value={repositoryUrl}
                    onChange={(e) =>
                        setRepositoryUrl(e.target.value)
                    }
                    />

                    <Button
                        variant="contained"
                        onClick={async () => {
                            const repo = await importRepository(repositoryUrl);
                            console.log(repo.id)
                            setRepositoryId(repo.id);
                        }}
                        >
                        Import Repository
                    </Button>
                </Stack>
            </CardContent>
        </Card>

        <Card>
            <CardContent>
            <Stack spacing={2}>
              <Typography variant="h6">
                Ask a Question
              </Typography>

              <TextField
                label="Question"
                fullWidth
                value={question}
                onChange={(e) =>
                  setQuestion(e.target.value)
                }
              />

            <Button
                variant="contained"
                onClick={async () => {
                if (!repositoryId) return;
                const result = await askRepository(repositoryId, question);
                setAnswer(result);
                }}
            >
            Ask
            </Button>

              <Box>
                <Typography variant="subtitle1">
                  Answer
                </Typography>

                <Typography>
                  {answer || "No answer yet."}
                </Typography>
              </Box>
            </Stack>
          </CardContent>
        </Card>
      </Stack>
    </Container>
  );
}