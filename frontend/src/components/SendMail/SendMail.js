import React from "react";
import "./SendMail.css";
import CloseIcon from "@mui/icons-material/Close";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import { closeSendMessage } from "../../features/mailSlice";
import {
  Button,
  MenuItem,
  Select,
  FormControl,
  InputLabel,
} from "@mui/material";

function SendMail() {
  const [selectedOption, setSelectedOption] = React.useState("Minor");

  const handleOptionChange = (event) => {
    setSelectedOption(event.target.value);
  };

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const dispatch = useDispatch();

  const onSubmit = async (data) => {
    const formData = new FormData();
    formData.append("senderEmail", "johndoe@example.com");
    formData.append("recipientEmail", data.recipientEmail);
    formData.append("subject", data.subject);
    formData.append("textBody", data.textBody);
    formData.append("sendTheEmail", data.sendTheEmail);
    formData.append("priority", selectedOption); // Include the priority
    if (data.attachments) {
      Array.from(data.attachments).forEach((file) =>
        formData.append("attachments", file)
      );
    }

    try {
      const response = await fetch("http://localhost:8080/api/emails/createEmail", {
        method: "POST",
        body: formData,
      });

      if (!response.ok) {
        const errorDetails = await response.json();
        throw new Error(errorDetails.message || "Failed to send email.");
      }
      alert("Email sent successfully!");
      dispatch(closeSendMessage());
    } catch (error) {
      console.error("Error sending email:", error);
      alert("Error sending email.");
    }
  };

  return (
    <div className="sendMail">
      <div className="sendMail-header">
        <h3>New Message</h3>
        <FormControl className="sendMail-dropdown" size="small">
          <InputLabel id="option-select-label">Priority</InputLabel>
          <Select
            labelId="option-select-label"
            value={selectedOption}
            onChange={handleOptionChange}
          >
            <MenuItem value="Minor">Minor</MenuItem>
            <MenuItem value="Moderate">Moderate</MenuItem>
            <MenuItem value="Urgent">Urgent</MenuItem>
            <MenuItem value="Important">Important</MenuItem>
          </Select>
        </FormControl>
        <CloseIcon
          onClick={() => dispatch(closeSendMessage())}
          className="sendMail-close"
        />
      </div>

      <form onSubmit={handleSubmit(onSubmit)}>
        <input
          name="recipientEmail"
          placeholder="To"
          type="email"
          {...register("recipientEmail", { required: "Recipient email is required!" })}
        />
        {errors.recipientEmail && (
          <p className="sendMail-error">{errors.recipientEmail.message}</p>
        )}
        <input
          name="subject"
          placeholder="Subject"
          type="text"
          {...register("subject", { required: "Subject is required!" })}
        />
        {errors.subject && (
          <p className="sendMail-error">{errors.subject.message}</p>
        )}
        <input
          name="textBody"
          placeholder="Message"
          type="text"
          className="sendMail-message"
          {...register("textBody", { required: "Message is required!" })}
        />
        {errors.textBody && (
          <p className="sendMail-error">{errors.textBody.message}</p>
        )}
        <input
          type="file"
          multiple
          {...register("attachments")}
        />
                <input
        type="hidden"
        value="true"
        {...register("sendTheEmail")}
        />
        <div className="sendMail-options">
          <Button
            type="submit"
            variant="contained"
            color="primary"
            className="sendMail-send"
          >
            Send
          </Button>
        </div>
      </form>
    </div>
  );
}

export default SendMail;