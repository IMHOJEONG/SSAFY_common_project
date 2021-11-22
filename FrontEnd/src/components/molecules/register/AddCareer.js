import { useState, useEffect } from "react";
import { useRecoilState } from "recoil";
import { AddCAREER } from "../../../recoil/atoms/AddCAREER";
import styled from "styled-components";
import "antd/dist/antd.css";
import { Button, Row, Col, Space } from "antd";

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

function AddCert() {
  const [form, setForm] = useState([]);
  const [careerInfo, setcareerInfo] = useRecoilState(AddCAREER);

  const prevIsValid = () => {
    if (form.length === 0) {
      return true;
    }

    const someEmpty = form.some(
      (item) =>
        item.enddate === "" ||
        item.startdate === "" ||
        item.role === "" ||
        item.company === ""
    );

    if (someEmpty) {
      form.map((item, index) => {
        const allPrev = [...form];

        if (form[index].company === "") {
          allPrev[index].errors.company = "company is required";
        }

        if (form[index].role === "") {
          allPrev[index].errors.role = "role is required";
        }

        if (form[index].startdate === "") {
          allPrev[index].errors.startdate = "startdate is required";
        }

        if (form[index].enddate === "") {
          allPrev[index].errors.enddate = "enddate is required";
        }

        setForm(allPrev);
      });
    }

    return !someEmpty;
  };

  const handleAddLink = (e) => {
    e.preventDefault();
    const inputState = {
      company: "",
      role: "",
      startdate: "",
      enddate: "",

      errors: {
        company: null,
        role: null,
        startdate: null,
        enddate: null,
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
        company: item["company"],
        role: item["role"],
        startdate: item["startdate"],
        enddate: item["enddate"],
      };
    });
    setcareerInfo(newForm);
  }, [form]);

  const handleRemoveField = (e, index) => {
    e.preventDefault();

    setForm((prev) => prev.filter((item) => item !== prev[index]));
  };

  const onInputFocus = (e) => {
    let target = e.target;
    target.type = 'date';
  }
  const onInputFocusOut = (e) => {
    let target = e.target;
    target.type = 'text';
  }

  return (
    <>
      <div>
        <form>
          {form.map((item, index) => (
            <Row key={`item-${index}`} style={{ display: "flex" }}>
              

              <Col span={14}
                style={{
                  width: "159px",
                  flex: "none"
                }}
                >
                  <StyledInput
                    type="text"
                    name="company"
                    placeholder="회사명"
                  value={item.company}
                  style={{
                    width: "150px"
                  }}
                    onChange={(e) => onChange(index, e)}
                  />

                  {item.errors.company && (
                    <div style={{ color: "red" }}>{item.errors.company}</div>
                  )}
                </Col>
              <Col span={10}
              >
                  <StyledInput
                    type="text"
                    name="role"
                    placeholder="직책"
                  value={item.role}
                  style={{
                    width: "150px"
                  }}
                    onChange={(e) => onChange(index, e)}
                  />

                  {item.errors.role && (
                    <div style={{ color: "red" }}>{item.errors.role}</div>
                  )}
                </Col>
              
              <Space>

                <Col span={10}>
                  <StyledInput
                    type="text"
                    name="startdate"
                    date-placeholder="시작일자"
                    placeholder="시작일자"
                    value={item.startdate}
                    style={{
                      width: "150px"
                    }}
                    onFocus={onInputFocus}
                    onBlur={onInputFocusOut}
                    onChange={(e) => onChange(index, e)}
                  />

                  {item.errors.startdate && (
                    <div style={{ color: "red" }}>{item.errors.startdate}</div>
                  )}
                </Col>
                <Col span={10}>
                  <StyledInput
                    type="text"
                    name="enddate"
                    date-placeholder="종료일자"
                    placeholder="종료일자"
                    value={item.enddate}
                    style={{
                      width: "150px"
                    }}
                    onFocus={onInputFocus}
                    onBlur={onInputFocusOut}
                    onChange={(e) => onChange(index, e)}
                  />

                  {item.errors.enddate && (
                    <div style={{ color: "red" }}>{item.errors.enddate}</div>
                  )}
                </Col>
                <Col span={4}>
                  <Button
                    type="primary"
                    danger
                    style={{
                      height: "45px"
                    }}
                    onClick={(e) => handleRemoveField(e, index)}
                  >
                    X
                  </Button>
                </Col>
              </Space>
            </Row>
          ))}

          <Button type="primary" onClick={handleAddLink}>
            커리어 추가
          </Button>
        </form>
      </div>
    </>
  );
}

export default AddCert;
