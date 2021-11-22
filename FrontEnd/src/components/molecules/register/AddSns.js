import { useState, useEffect } from "react";
import { useRecoilState } from "recoil";
import { AddSNS } from "../../../recoil/atoms/AddSNS";
import styled from "styled-components";
import "antd/dist/antd.css";
import { Button, Space } from "antd";
import arrow from "../../../assets/pages/register/arrow.jpg";

const StyledInput = styled.input`
  display: block;
  box-sizing: border-box;
  width: 100%;
  border-radius: 4px;
  border: 1px solid black;
  padding: 10px 10px;
  margin-bottom: 10px;
  font-size: 14px;
  color: black;
`;

const StyledSelect = styled.select`
  width: 200px;
  padding: 0.8em 0.5em;
  border: 1px solid #999;
  font-family: inherit;
  background: url(${arrow}) no-repeat 95% 50%;
  border-radius: 0px;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;

  .select::-ms-expand {
    display: none;
  }
`;

function AddSns() {
  const [form, setForm] = useState([]);
  const [snsInfo, setsnsInfo] = useRecoilState(AddSNS);

  const prevIsValid = () => {
    if (form.length === 0) {
      return true;
    }

    const someEmpty = form.some(
      (item) => item.SNSUrl === "" || item.SNSPlatform === ""
    );

    if (someEmpty) {
      form.map((item, index) => {
        const allPrev = [...form];

        if (form[index].SNSPlatform === "") {
          allPrev[index].errors.SNSPlatform = "SNSPlatform is required";
        }

        if (form[index].SNSUrl === "") {
          allPrev[index].errors.SNSUrl = "SNSUrl is required";
        }
        setForm(allPrev);
      });
    }

    return !someEmpty;
  };

  const handleAddLink = (e) => {
    e.preventDefault();
    const inputState = {
      SNSPlatform: "",
      SNSUrl: "",

      errors: {
        SNSPlatform: null,
        SNSUrl: null,
      },
    };

    if (prevIsValid()) {
      setForm((prev) => [...prev, inputState]);
    }
  };

  const onChange = (index, event) => {
    event.preventDefault();
    event.persist();

    setForm((prev) => {
      return prev.map((item, i) => {
        if (i !== index) {
          return item;
        }

        return {
          ...item,
          [event.target.name]: event.target.value,

          errors: {
            ...item.errors,
            [event.target.name]:
              event.target.value.length > 0
                ? null
                : [event.target.name] + " Is required",
          },
        };
      });
    });
  };

  useEffect(() => {
    const newForm = form.map((item, index) => {
      return {
        platform: item["SNSPlatform"],
        url: item["SNSUrl"],
      };
    });
    setsnsInfo(newForm);
  }, [form]);

  const handleRemoveField = (e, index) => {
    e.preventDefault();

    setForm((prev) => prev.filter((item) => item !== prev[index]));
  };
  return (
    <>
      <div>
        <form>
          {form.map((item, index) => (
            <div key={`item-${index}`} style={{ display: "flex" }}>
              <Space>

              <div>
                <StyledSelect
                  name="SNSPlatform"
                  value={item.SNSPlatform}
                  onChange={(e) => onChange(index, e)}
                  >
                  <option value="">Select</option>
                  <option value="facebook">페이스북</option>
                  <option value="instagram">인스타그램</option>
                  <option value="twitter">트위터</option>
                  <option value="youtube">유튜브</option>
                </StyledSelect>
                {item.errors.SNSPlatform && (
                  <div style={{ color: "red" }}>{item.errors.SNSPlatform}</div>
                  )}
              </div>

              <div>
                <StyledInput
                  type="text"
                  name="SNSUrl"
                  placeholder="SNS주소"
                  value={item.SNSUrl}
                  onChange={(e) => onChange(index, e)}
                    style={{
                      marginTop: "10px"
                  }}
                  />

                {item.errors.SNSUrl && (
                  <div style={{ color: "red" }}>{item.errors.SNSUrl}</div>
                  )}
              </div>

              <Button
                type="primary"
                danger
                onClick={(e) => handleRemoveField(e, index)}
                >
                X
              </Button>
                </Space>
            </div>
          ))}

          <Button type="primary" onClick={handleAddLink}
            style={{
              marginBottom: "10px"
          }}>
            SNS 추가
          </Button>
        </form>
      </div>
    </>
  );
}

export default AddSns;
